        package com.example.trialapp.ui.home
        import com.google.maps.android.SphericalUtil


        import android.app.Activity
        import android.content.ContentValues.TAG
        import android.content.Context
        import android.content.Intent
        import android.content.pm.PackageManager
        import android.location.Location
        import android.os.Bundle
        import android.util.Log
        import android.view.LayoutInflater
        import android.view.View
        import android.view.ViewGroup
        import android.widget.Button
        import android.widget.Spinner
        import android.widget.TextView
        import android.widget.Toast
        import androidx.core.app.ActivityCompat
        import androidx.core.content.ContextCompat
        import androidx.fragment.app.Fragment
        import androidx.lifecycle.ViewModelProvider
        import com.example.trialapp.BuildConfig.PLACES_API_KEY
        import com.example.trialapp.Reservation
        import com.example.trialapp.ReservationRepository
        import com.example.trialapp.databinding.FragmentHomeBinding
        import com.example.trialapp.ui.Remote.ApiService
        import com.example.trialapp.ui.Remote.Checkin
        import com.example.trialapp.ui.Remote.Login
        import com.example.trialapp.ui.Remote.Network
        import com.example.trialapp.ui.SessionManager
        import com.google.android.gms.location.FusedLocationProviderClient
        import com.google.android.gms.location.LocationServices
        import com.google.android.gms.maps.model.LatLng
        import com.google.android.gms.maps.model.LatLngBounds
        import com.google.android.libraries.places.api.Places
        import com.google.android.libraries.places.api.model.Place
        import com.google.android.libraries.places.api.model.RectangularBounds
        import com.google.android.libraries.places.widget.Autocomplete
        import com.google.android.libraries.places.widget.AutocompleteActivity
        import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
        import retrofit2.Call
        import retrofit2.Response
        import java.text.SimpleDateFormat
        import java.util.Calendar
        import java.util.Locale


        class HomeFragment : Fragment() {

//            var sessionManager = getContext()?.let { SessionManager(it) }

            private var selectedPlaceId: String? = null
            private var selectedPlaceName: String? = null

            private var _binding: FragmentHomeBinding? = null
            private val binding get() = _binding!!
            private var currentLocation: Location? = null
            private fun getCurrentLocation(onLocationRetrieved: (Location?) -> Unit) {

                if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        currentLocation = location
                        onLocationRetrieved(location)
                        if (location == null) {
                            Toast.makeText(context, "Location not available", Toast.LENGTH_LONG).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(context, "Failed to get location", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, "Location permission not granted", Toast.LENGTH_LONG).show()
                    onLocationRetrieved(null)
                }
            }





            private lateinit var fusedLocationClient: FusedLocationProviderClient
            private val AUTOCOMPLETE_REQUEST_CODE = 1

            override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
            ): View {
//                if (sessionManager!!.isLoggedIn) {
//                    val userName = sessionManager!!.userName
//                    // Use userName or other session data as needed
//                }
//                println("*****************************")
//                println(sessionManager!!.userName)
//                println("*****************************")
                Places.initialize(requireContext(), PLACES_API_KEY)
                Log.d("HomeFragment", "Current location: $currentLocation")
        //        getCurrentLocation()
        //        println("############################# $currloc ################################")
                println("***************************** $currentLocation  ************************************")

                val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

                _binding = FragmentHomeBinding.inflate(inflater, container, false)
                val root: View = binding.root

                fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

                checkLocationPermission()

                // Retrieve the personnel name and rank from SharedPreferences
                val sharedPreferences = requireActivity().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
                val personnelName = sharedPreferences.getString("givenName", "N/A") + " " + sharedPreferences.getString("lastName", "")
                val personnelRank = sharedPreferences.getString("militaryRank", "N/A")

                // Update the UI with the retrieved values
                val editTextPersonnelName: TextView = binding.editTextPersonnelName
                val editTextRank: TextView = binding.editTextRank

                editTextPersonnelName.text = personnelName
                editTextRank.text = personnelRank

                homeViewModel.text.observe(viewLifecycleOwner) {
                    binding.textHome.text = it
                }


                val textView: TextView = binding.textHome

                homeViewModel.text.observe(viewLifecycleOwner) {
                    textView.text = it
                }

                val editTextSelectLocation: TextView = binding.editTextSelectLocation
                editTextSelectLocation.isEnabled = false

                getCurrentLocation { location ->
                    if (location != null) {
                        // Enable editTextSelectLocation only when the location is available
                        editTextSelectLocation.isEnabled = true
                        setupLocationEditText(location)
                    } else {
                        Toast.makeText(context, "Current location is not available", Toast.LENGTH_LONG).show()
                    }
                }






                val spinnerPurpose: Spinner = binding.spinnerPurpose
                val buttonCheckIn: Button = binding.buttonCheckIn


                buttonCheckIn.setOnClickListener {
                    if (isWithin5Miles()) {
                        // Retrieve the username from SharedPreferences
                        val sharedPreferences = requireContext().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
                        val currentUserName = sharedPreferences.getString("username", "defaultUsername") ?: "defaultUsername" // Fallback to "defaultUsername" if null

                        val checkin = Checkin().apply {
                            this.googlePlaceId = selectedPlaceId
                            this.placeName = selectedPlaceName
                            this.userName = currentUserName
                        }

                        println("UserName: ${checkin.userName}")

                        // Make a network call to check in
                        val apiService = Network.getInstance().create(ApiService::class.java)
                        apiService.checkinUser(checkin).enqueue(object : retrofit2.Callback<Checkin> {
                            override fun onResponse(call: Call<Checkin>, response: Response<Checkin>) {
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "Check-in successful", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, "Check-in failed", Toast.LENGTH_LONG).show()
                                }
                            }

                            override fun onFailure(call: Call<Checkin>, t: Throwable) {
                                Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show()
                            }
                        })
                    } else {
                        Toast.makeText(context, "You must be within 5 miles to check in.", Toast.LENGTH_LONG).show()
                    }
                }


                return root
            }



//            private fun setupLocationEditText(location: Location) {
//                val editTextSelectLocation: TextView = binding.editTextSelectLocation
//
//                editTextSelectLocation.setOnClickListener {
//                    if (currentLocation == null) {
//                        Toast.makeText(context, "Current location is not available", Toast.LENGTH_LONG).show()
//                        return@setOnClickListener
//                    }
//
//                    val latRadian = Math.toRadians(currentLocation!!.latitude)
//                    val degLatKm = 110.574235  // 1 degree latitude in kilometers
//                    val degLongKm = 110.572833 * Math.cos(latRadian)  // 1 degree longitude in kilometers
//                    val deltaLat = (0.5 / degLatKm)  // 0.5 miles in degrees latitude
//                    val deltaLong = (0.5 / degLongKm)  // 0.5 miles in degrees longitude
//
//                    // Set up the rectangular bounds for the location bias
//                    val southwest = LatLng(currentLocation!!.latitude - deltaLat, currentLocation!!.longitude - deltaLong)
//                    val northeast = LatLng(currentLocation!!.latitude + deltaLat, currentLocation!!.longitude + deltaLong)
//
//                    val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
//                    val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//                        .setLocationBias(RectangularBounds.newInstance(southwest, northeast))
//                        .build(requireContext())
//                    startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
//                }
//            }
//
//
//            private fun launchAutocomplete() {
//                if (currentLocation == null) {
//                    Toast.makeText(context, "Current location is not available", Toast.LENGTH_LONG).show()
//                    return
//                }
//
//                val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
//                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//                    .setLocationBias(RectangularBounds.newInstance(
//                        LatLng(currentLocation!!.latitude - 0.004491556, currentLocation!!.longitude - 0.004491556),
//                        LatLng(currentLocation!!.latitude + 0.004491556, currentLocation!!.longitude + 0.004491556)))
//                    .build(requireContext())
//                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
//            }
private fun setupLocationEditText(location: Location) {
    val editTextSelectLocation: TextView = binding.editTextSelectLocation
    editTextSelectLocation.setOnClickListener {
        if (currentLocation == null) {
            Toast.makeText(context, "Current location is not available", Toast.LENGTH_LONG).show()
            return@setOnClickListener
        }
        launchAutocomplete(currentLocation!!)
    }
}

            private fun launchAutocomplete(location: Location) {
                val radiusInMeters = 804.672  // 0.5 miles in meters
                val latLngBounds = getBounds(location.latitude, location.longitude, radiusInMeters)

                val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .setLocationBias(RectangularBounds.newInstance(latLngBounds))
                    .build(requireContext())
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
            }

            // Function to calculate bounds for given radius around a point
            private fun getBounds(latitude: Double, longitude: Double, radiusInMeters: Double): LatLngBounds {
                val distanceFromCenterToCorner = radiusInMeters * Math.sqrt(2.0)
                val southwestCorner = SphericalUtil.computeOffset(LatLng(latitude, longitude), distanceFromCenterToCorner, 225.0)
                val northeastCorner = SphericalUtil.computeOffset(LatLng(latitude, longitude), distanceFromCenterToCorner, 45.0)
                return LatLngBounds(southwestCorner, northeastCorner)
            }





            override fun onDestroyView() {
                super.onDestroyView()
                _binding = null
            }



            private fun isWithin5Miles(): Boolean {
                // Placeholder for your logic to check if the user is within 5 miles
                // This should involve getting the current location of the user
                // and comparing it to the target location
                return true // This should be replaced with the actual check
            }

            private fun checkLocationPermission() {
                if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
                }
            }

            private fun isWithin5Miles(targetLat: Double, targetLon: Double, completion: (Boolean) -> Unit) {
                if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        location?.let {
                            val results = FloatArray(1)
                            Location.distanceBetween(it.latitude, it.longitude, targetLat, targetLon, results)
                            val distanceInMeters = results[0]
                            completion(distanceInMeters <= 8050) // 8050 meters is approximately 5 miles
                        } ?: run {
                            Toast.makeText(context, "Location not available", Toast.LENGTH_LONG).show()
                            completion(false)
                        }
                    }
                } else {
                    Toast.makeText(context, "Location permission not granted", Toast.LENGTH_LONG).show()
                    completion(false)
                }
            }

            override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                if (requestCode == 1) {
                    if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                        // Permission was granted, you can now proceed with obtaining the location
                    } else {
                        // Permission was denied, handle this appropriately
                        Toast.makeText(context, "Location permission is required", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)

                if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        binding.editTextSelectLocation.text = place.name
                        Log.i(TAG, "Place: ${place.name}, ${place.id}")

                        // Store the place ID and name for later use
                        selectedPlaceId = place.id
                        selectedPlaceName = place.name
                    } else if (resultCode == AutocompleteActivity.RESULT_ERROR && data != null) {
                        // Handle the error.
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(TAG, "Error: ${status.statusMessage}")
                    }
                }
            }




        }

