package com.example.trialapp.ui.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.trialapp.R // Use your application's package name
import android.widget.TextView
import android.util.Log

import com.example.trialapp.ui.Remote.Reservation

class ReservationAdapter(context: Context, reservations: List<Reservation>)
    : ArrayAdapter<Reservation>(context, 0, reservations) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.reservation_list_item, parent, false)

        val reservation = getItem(position)
        val placeNameTextView = listItemView.findViewById<TextView>(R.id.placeNameTextView)
        val checkinTimeTextView = listItemView.findViewById<TextView>(R.id.checkinTimeTextView)
        val checkoutTimeTextView = listItemView.findViewById<TextView>(R.id.checkoutTimeTextView)

        Log.d("ReservationAdapter", "Setting text for position $position: ${reservation?.placeName}")
        placeNameTextView.text = reservation?.placeName
        checkinTimeTextView.text = reservation?.checkinTime
        checkoutTimeTextView.text = reservation?.checkoutTime

        return listItemView
    }
}
