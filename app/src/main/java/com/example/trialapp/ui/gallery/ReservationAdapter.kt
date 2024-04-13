package com.example.trialapp.ui.gallery
import com.example.trialapp.ui.Remote.User

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.trialapp.R
import com.example.trialapp.ui.LocationDetailActivity
import com.example.trialapp.ui.Remote.Reservation
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReservationAdapter(context: Context, reservations: List<Reservation>)
    : ArrayAdapter<Reservation>(context, 0, reservations.sortedByDescending {
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(it.checkinTime) ?: Date(0)
}) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItemView = convertView ?: inflater.inflate(R.layout.reservation_list_item, parent, false)
        val reservation = getItem(position) ?: return listItemView

        listItemView.findViewById<TextView>(R.id.placeNumberTextView).text = "${position + 1}."
        listItemView.findViewById<TextView>(R.id.placeNameTextView).text = reservation.placeName
        listItemView.findViewById<TextView>(R.id.checkinTimeTextView).text = "Check-in: ${formatDate(reservation.checkinTime)}"
        listItemView.findViewById<TextView>(R.id.checkoutTimeTextView).text = "Check-out: ${formatDate(reservation.checkoutTime)}"

        // Determine background color based on check-out status
        val backgroundColorId = if (hasCheckedOut(reservation.checkoutTime)) {
            R.color.checked_out_color
        } else {
            R.color.checked_in_color
        }
        listItemView.setBackgroundColor(ContextCompat.getColor(context, backgroundColorId))

        // Only set the click listener for items where the user hasn't checked out
        if (!hasCheckedOut(reservation.checkoutTime)) {
            listItemView.setOnClickListener {
                val intent = Intent(context, LocationDetailActivity::class.java).apply {
                    putExtra("placeId", reservation.googlePlaceId)
                    putExtra("locationName", reservation.placeName)
                }
                context.startActivity(intent)
            }
        } else {
            // Disable click for checked-out items
            listItemView.setOnClickListener(null)
        }

        return listItemView
    }

    private fun formatDate(dateStr: String?): String {
        if (dateStr == null) return "Unknown"
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
        return try {
            val parsedDate = inputFormat.parse(dateStr)
            outputFormat.format(parsedDate ?: return "Unknown")
        } catch (e: ParseException) {
            "Unknown"
        }
    }

    private fun hasCheckedOut(checkoutTimeStr: String?): Boolean {
        if (checkoutTimeStr == null) return false
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        return try {
            val checkoutTime = format.parse(checkoutTimeStr)
            val currentTime = Date()
            checkoutTime != null && checkoutTime.before(currentTime)
        } catch (e: ParseException) {
            false
        }
    }
}


class UserAdapter(context: Context, users: List<User>) : ArrayAdapter<User>(context, 0, users) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false)
        }

        val user = getItem(position)
        listItemView?.findViewById<TextView>(R.id.detailUsernameTextView)?.text = user?.givenName
        listItemView?.findViewById<TextView>(R.id.detailUserLastNameTextView)?.text = user?.lastName
        listItemView?.findViewById<TextView>(R.id.detailUserRankTextView)?.text = user?.militaryRank

        return listItemView!!
    }
}

