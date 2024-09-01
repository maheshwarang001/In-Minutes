package com.example.rider_app.network.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import com.example.rider_app.util.hasLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultLocationClient(
            private val context : Context,
            private val client : FusedLocationProviderClient
            ): LocationClient
{
    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long): Flow<Location> {
        return callbackFlow {
            if(!context.hasLocationPermission()){
                Log.e("location","Missing permission")
                throw LocationClient.LocationException("Missing location")
            }


            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if(!isNetworkEnabled && !isGpsEnabled){
                Log.e("location","Missing client")
                throw LocationClient.LocationException("GPS Missing")

            }
            val request = LocationRequest
                .create()
                .setInterval(interval)
                .setFastestInterval(interval)

            val locationCallback = object  : LocationCallback(){
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location ->
                        launch { send(location) }
                    }
                }
            }

            client.requestLocationUpdates(request,locationCallback, Looper.getMainLooper())

            awaitClose{
                client.removeLocationUpdates(locationCallback)
            }
        }
    }
}