package com.example.rider_app.network.websockets

import okio.ByteString

interface WebSocketManager {

    fun startWebSocket(url:String)
    fun sendMessage(message: ByteString)
    fun closeWebSocket()
    fun onWebSocketFailure()
}