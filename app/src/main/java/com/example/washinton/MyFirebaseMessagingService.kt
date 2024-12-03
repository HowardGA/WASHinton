package com.example.washinton

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token: $token")
        // Send token to your server if needed
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "Message received from: ${remoteMessage.from}")
        Log.d("FCM", "Received data: ${remoteMessage.data}")


        // Optionally, show a notification
        val title = remoteMessage.data["title"] ?: "New Notification"
        val body = remoteMessage.data["body"] ?: "You have a new message."
        val orderID = try {
            val dataJson = remoteMessage.data["data"] ?: "{}" // Get the "data" JSON string
            val dataObject = JSONObject(dataJson) // Parse the JSON string
            dataObject.getString("orderID") // Extract the "orderID" value
        } catch (e: JSONException) {
            "0" // Default value if parsing fails
        }

        Log.d("FCM", "Received OrderID: $orderID")

        showNotification(title, body)


    }

    private fun showNotification(title: String, message: String) {
        val channelId = "default_channel_id"
        val notificationId = (System.currentTimeMillis() % Int.MAX_VALUE).toInt() // Unique notification ID

        // Check and request permission for notifications on Android 13+ (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.e("FCM", "Notification permission not granted.")
            return
        }

        // Create the notification channel for Android 8.0+ (OREO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "General Notifications"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Build and show the notification
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.notification_icon)
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.notification_cleanning) // Ensure the icon is valid
            .setLargeIcon(largeIcon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true) // Dismiss notification on click
            .build()

        NotificationManagerCompat.from(this).notify(notificationId, notification)
        Log.d("FCM", "Notification shown with ID: $notificationId")
    }
}

