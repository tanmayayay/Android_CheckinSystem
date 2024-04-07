//package com.example.trialapp.ui.gallery
//
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import android.widget.ListView
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.trialapp.Reservation
//import com.example.trialapp.databinding.FragmentGalleryBinding
//import com.example.trialapp.ReservationRepository
//import com.example.trialapp.ui.Remote.ApiService
//import com.example.trialapp.ui.Remote.Network
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//
//class GalleryFragment : Fragment() {
//
//    private var _binding: FragmentGalleryBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val galleryViewModel =
//            ViewModelProvider(this).get(GalleryViewModel::class.java)
//
//        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textGallery
//        galleryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        return root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val listView: ListView = binding.reservationListView
//        val adapter = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_list_item_1,
//            ReservationRepository.getReservations().map {
//                "${it.personnelName}, ${it.rank}, ${it.purpose}, ${it.checkInTime}"
//            }
//        )
//        listView.adapter = adapter
//    }
//
//    private fun fetchReservations() {
//        val apiService = Network.getInstance().create(ApiService::class.java)
//        val sharedPreferences = requireActivity().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
//        val username = sharedPreferences.getString("username", "") ?: ""
//
//        apiService.getReservations(username).enqueue(object : Callback<List<Reservation>> {
//            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
//                // Handle response
//            }
//
//            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
//                // Handle failure
//            }
//        })
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
package com.example.trialapp.ui.gallery

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.trialapp.databinding.FragmentGalleryBinding
import com.example.trialapp.ui.Remote.ApiService
import com.example.trialapp.ui.Remote.Network
import com.example.trialapp.ui.Remote.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryFragment : Fragment() {
    private var binding: FragmentGalleryBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        galleryViewModel.text.observe(viewLifecycleOwner) { text ->
            binding!!.textGallery.text = text
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchReservations()
    }

    private fun fetchReservations() {
        val apiService = Network.getInstance().create(ApiService::class.java)
        val sharedPreferences = requireContext().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val currentUserName = sharedPreferences.getString("username", "defaultUsername") ?: "defaultUsername"

        apiService.getReservations(currentUserName).enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
                if (response.isSuccessful && response.body() != null) {
                    updateUI(response.body()!!)
                } else {
                    Log.e("GalleryFragment", "Response unsuccessful or body is null")
                }
            }

            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                Log.e("GalleryFragment", "API call failed", t)
            }
        })
    }

    private fun updateUI(reservations: List<Reservation>) {
        val adapter = ReservationAdapter(requireContext(), reservations)
        binding?.reservationListView?.adapter = adapter
        binding?.reservationListView?.setOnItemClickListener { _, _, position, _ ->
            val reservation = reservations[position]
            // Implement navigation to detail fragment or activity, passing reservation data
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
