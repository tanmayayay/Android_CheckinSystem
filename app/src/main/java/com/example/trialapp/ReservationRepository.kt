package com.example.trialapp


object ReservationRepository {

    private val reservations = mutableListOf<Reservation>()

    fun addReservation(reservation: Reservation) {
        reservations.add(reservation)
    }

    fun getReservations(): List<Reservation> {
        return reservations
    }
}
