package com.example.rider_app.network.foreground

import WebSocketListenerImpl
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.rider_app.R
import com.example.rider_app.model.SocketLocation
import com.example.rider_app.network.websockets.DefaultWebSocketManager
import com.example.rider_app.network.websockets.WebSocketManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okio.ByteString
import java.nio.ByteBuffer

class WebSocketService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var webSocketManager: WebSocketManager
    private val binder = WebSocketBinder()

    override fun onCreate() {
        super.onCreate()
        webSocketManager = DefaultWebSocketManager(WebSocketListenerImpl(this))
        startForegroundService()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startWebSocketConnection()
            ACTION_STOP -> stopWebSocketConnection()
        }
        return START_STICKY
    }

    private fun startWebSocketConnection() {
        Log.i("WebSocketListener", "foreground Request init")

        serviceScope.launch {
            val webSocketUrl = "ws://10.0.2.2:9090/rider/socket?userId=233423424"
            webSocketManager.startWebSocket(webSocketUrl)
        }
    }

    fun retryOnFailure(){
        webSocketManager.onWebSocketFailure()
    }

    private fun stopWebSocketConnection() {

        webSocketManager.closeWebSocket()
        stopForeground(true)
        stopSelf()
    }

    private fun startForegroundService() {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, "websocket_channel")
            .setContentTitle("WebSocket Connection")
            .setContentText("WebSocket is running in the background")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "websocket_channel",
                "WebSocket Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    fun sendMessage(socketLocation: SocketLocation){
        val buffer = ByteBuffer.allocate(16)
        buffer.putDouble(socketLocation.latitude)
        buffer.putDouble(socketLocation.longitude)

        val byteString = ByteString.of(*buffer.array())
        webSocketManager.sendMessage(byteString)
    }

    override fun onDestroy() {
        super.onDestroy()

        serviceScope.cancel()

        webSocketManager.closeWebSocket()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }



    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        const val ACTION_SEND = "ACTION_SEND"

    }
    inner class WebSocketBinder : Binder() {
        fun getService(): WebSocketService = this@WebSocketService
    }
}
