package com.example.trialapp

data class Reservation(
    val personnelName: String,
    val rank: String,
    val purpose: String,
    val checkInTime: String,
    var checkOutTime: String? = null // Add this line

)
