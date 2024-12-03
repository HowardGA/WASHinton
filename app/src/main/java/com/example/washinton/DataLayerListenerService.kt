package com.example.washinton

import android.content.Intent
import android.util.Log
import com.example.washinton.MainActivity
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class DataLayerListenerService : WearableListenerService() {

    private val TAG = "MobileApp"

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == "/send-to-mobile") {
            val message = String(messageEvent.data)
            Log.d(TAG, "Message received: $message")

            // Handle the message, e.g., start an activity or update the backend
            handleMessage(message)
        } else {
            super.onMessageReceived(messageEvent)
        }
    }

    private fun handleMessage(message: String) {
        // Example: Start an activity with the message
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("message", message)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)

        // Alternatively, send the message to your backend via API
    }
}
