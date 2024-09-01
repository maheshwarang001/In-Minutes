import android.util.Log
import com.example.rider_app.network.foreground.WebSocketService
import com.example.rider_app.network.websockets.DefaultWebSocketManager
import com.example.rider_app.network.websockets.WebSocketManager
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketListenerImpl(private val websSocketService: WebSocketService) : WebSocketListener( ) {

    companion object {
        const val TAG = "WebSocketListener"
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d(TAG, "WebSocket opened with response: $response")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d(TAG, "Message received: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Log.d(TAG, "Binary message received: ${bytes.hex()}")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Log.d(TAG, "WebSocket is closing. Code: $code, Reason: $reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.d(TAG, "WebSocket closed. Code: $code, Reason: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.e(TAG, "WebSocket failure: ${t.message}", t)
        websSocketService.retryOnFailure()

        response?.let {
            Log.e(TAG, "Response: $response")
        }
    }
}
