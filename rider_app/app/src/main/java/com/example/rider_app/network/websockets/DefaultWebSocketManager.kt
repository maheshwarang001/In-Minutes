package com.example.rider_app.network.websockets

import WebSocketListenerImpl
import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okio.ByteString
import java.util.concurrent.TimeUnit
import kotlin.math.pow

class DefaultWebSocketManager(
    private val webSocketListenerImpl: WebSocketListenerImpl
): WebSocketManager {

    private var retryCount = 0
    private val maxRetries = 5
    private lateinit var webSocket: WebSocket
    private val client : OkHttpClient = OkHttpClient

        .Builder()
        .pingInterval(30,TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    override fun startWebSocket(url: String) {
        Log.i("WebSocketListener", "Request init")
        val request = Request.Builder()
            .url(url)
            .build()

        webSocket = client.newWebSocket(request,webSocketListenerImpl)
    }

    override fun sendMessage(message: ByteString) {
        webSocket.send(message)
    }

    override fun closeWebSocket() {
        webSocket.close(1000,"Closing webSocket connection")
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun retryConnection(url: String) {
        if (retryCount < maxRetries) {
            val delayTime = (2.0.pow(retryCount.toDouble()) * 10000).toLong()
            retryCount++

            GlobalScope.launch {
                delay(delayTime)
                startWebSocket(url)
            }
        } else {
            Log.e("WebSocketListener", "Max retries reached. Could not reconnect.")
        }
    }


    override  fun onWebSocketFailure() {
        retryConnection("ws://10.0.2.2:9090/rider/socket?userId=233423424")
    }





}