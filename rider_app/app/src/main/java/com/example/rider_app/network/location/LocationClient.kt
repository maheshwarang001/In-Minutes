package com.example.rider_app.network.location

import android.location.Location
import kotlinx.coroutines.flow.Flow


interface LocationClient {

    fun getLocationUpdates(interval: Long) : Flow<Location>

    class LocationException(message: String) : Exception()
}