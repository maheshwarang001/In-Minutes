package com.example.rider_app.network.foreground

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.rider_app.R
import com.example.rider_app.model.SocketLocation
import com.example.rider_app.network.location.DefaultLocationClient
import com.example.rider_app.network.location.LocationClient
import com.example.rider_app.network.websockets.WebSocketManager
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class LocationService: Service() {

    private val serviceScope = CoroutineScope(SupervisorJob()+Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private var webSocketService: WebSocketService? = null
    private var bound = false

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )

        val intent = Intent(this, WebSocketService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)

    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as WebSocketService.WebSocketBinder
            webSocketService = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            webSocketService = null
            bound = false
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
       when(intent?.action){

           ACTION_START -> start()
           ACTION_STOP -> stop()
       }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(){
        Log.i("start location", "dsadadas")

        val notification = NotificationCompat.Builder (this, "location")
            .setContentTitle("Rider profile active")
            .setContentTitle("Visit app for more")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)

        locationClient
            .getLocationUpdates(40000L)
            .catch { e->e.printStackTrace() }
            .onEach { location->
                val lat = location.latitude
                val long = location.longitude


               webSocketService?.sendMessage(SocketLocation(lat,long))

            }.launchIn(serviceScope)


        startForeground(1,notification.build())
    }

    private fun stop(){
        Log.i("stop location", "dsadadas")

        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }


    companion object{
        const val ACTION_START = "ACTION START"
        const val ACTION_STOP = "ACTION STOP"

    }

}