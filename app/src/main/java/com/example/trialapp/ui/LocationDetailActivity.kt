package com.example.trialapp.ui

import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trialapp.R
import com.example.trialapp.ui.Remote.ApiService
import com.example.trialapp.ui.Remote.Network
import com.example.trialapp.ui.Remote.User
import com.example.trialapp.ui.gallery.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_detail)

        val locationName = intent.getStringExtra("locationName") ?: "Unknown Location"
        val placeId = intent.getStringExtra("placeId") ?: ""

        val locationNameTextView = findViewById<TextView>(R.id.detailPlaceNameTextView)
        locationNameTextView.text = locationName

        if (placeId.isNotEmpty()) {
            fetchActiveReservations(placeId)
        } else {
            // Consider displaying an error message or closing the activity with a message
            // as placeId is essential for fetching data.
        }
    }

    private fun fetchActiveReservations(placeId: String) {
        val apiService = Network.getInstance().create(ApiService::class.java)
        apiService.getActiveReservations(placeId).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful && response.body() != null) {
                    updateUI(response.body()!!)
                } else {
                    // Handle the case where the response is not successful
                    // Maybe show a message indicating no data or an error occurred.
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Handle failure in API call
                // Consider showing an error message to the user.
            }
        })
    }

    private fun updateUI(users: List<User>) {
        val adapter = UserAdapter(this, users)
        val usersListView = findViewById<ListView>(R.id.usersListView)
        usersListView.adapter = adapter
    }
}
