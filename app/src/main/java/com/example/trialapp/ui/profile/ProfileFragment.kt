//package com.example.trialapp.ui.profile
//
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import com.example.trialapp.databinding.FragmentProfileBinding
//import org.json.JSONObject
//
//class ProfileFragment : Fragment() {
//
//    private var _binding: FragmentProfileBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentProfileBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val sharedPreferences = activity?.getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
//        val loggedInUsername = sharedPreferences?.getString("loggedInUser", "") // Assuming you save the currently logged-in user's username under "loggedInUser"
//        val userDetails = JSONObject(sharedPreferences?.getString(loggedInUsername, "")!!)
//
//        val givenName = userDetails.getString("givenName")
//        val lastName = userDetails.getString("lastName")
//        val militaryId = userDetails.getString("militaryId")
//        val rank = userDetails.getString("rank")
//
//        binding.textViewGivenName.text = "Given Name: $givenName"
//        binding.textViewLastName.text = "Last Name: $lastName"
//        binding.textViewMilitaryID.text = "Military ID: $militaryId"
//        binding.textViewRank.text = "Rank: $rank"
//
//        // TODO: Load the profile image for the user if you have one.
//        // For now, I've added a placeholder image in the XML layout.
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}

package com.example.trialapp.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trialapp.R
import com.example.trialapp.databinding.FragmentProfileBinding
import com.example.trialapp.ui.Remote.ApiService
import com.example.trialapp.ui.Remote.Network
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//
//class ProfileFragment : Fragment() {
//    private var binding: FragmentProfileBinding? = null
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentProfileBinding.inflate(inflater, container, false)
//        return binding!!.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        fetchUserProfile()
//    }
//
//    private fun fetchUserProfile() {
//        val apiService = Network.getInstance().create(
//            ApiService::class.java
//        )
//        // Replace "defaultUser" with logic to retrieve the logged-in user's username
//        val call: Call<JsonObject> = apiService.getUserProfile("defaultUser")
//        call.enqueue(object : Callback<JsonObject?> {
//            override fun onResponse(call: Call<JsonObject?>, response: Response<JsonObject?>) {
//                if (response.isSuccessful && response.body() != null) {
//                    displayUserProfile(response.body())
//                } else {
//                    // Handle the error scenario
//                }
//            }
//
//            override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
//                // Handle the failure scenario
//            }
//        })
//    }
//
//    private fun displayUserProfile(userProfile: JsonObject?) {
//        val givenName = userProfile!!["givenName"].asString
//        val lastName = userProfile["lastName"].asString
//        val militaryId = userProfile["militaryId"].asString
//        val rank = userProfile["militaryRank"].asString
//        binding!!.textViewGivenName.text = "Given Name: $givenName"
//        binding!!.textViewLastName.text = "Last Name: $lastName"
//        binding!!.textViewMilitaryID.text = "Military ID: $militaryId"
//        binding!!.textViewRank.text = "Rank: $rank"
//
//        // If you have a logic to display the profile image, implement it here
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }
//}
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Use requireContext() to get the Context from the fragment
        val sharedPreferences = requireContext().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "defaultUsername") ?: "defaultUsername" // Providing a default value and ensuring it's not null

        // Now you can use the username to fetch the user profile
        fetchUserProfile(username)
    }

    private fun fetchUserProfile(username: String) {
        val apiService = Network.getInstance().create(ApiService::class.java)
        val call = apiService.getUserProfile(username)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("ProfileFragment", "API Response: ${response.body()}")
                    displayUserProfile(response.body()!!)
                } else {
                    Log.e("ProfileFragment", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("ProfileFragment", "API Call Failed", t)
            }
        })
    }


    private fun displayUserProfile(userProfile: JsonObject) {
        val givenName = userProfile.get("givenName").asString
        val lastName = userProfile.get("lastName").asString
        val militaryId = userProfile.get("militaryId").asString
        val rank = userProfile.get("militaryRank").asString  // Ensure the key matches what's in the JSON

        // Update UI elements
        binding.textViewGivenName.text = "Given Name: $givenName"
        binding.textViewLastName.text = "Last Name: $lastName"
        binding.textViewMilitaryID.text = "Military ID: $militaryId"
        binding.textViewRank.text = "Rank: $rank"

        // Set the profile image
        binding.profileImageView.setImageResource(R.drawable.default_profile_photo)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
