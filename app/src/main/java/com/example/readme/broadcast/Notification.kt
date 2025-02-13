package com.example.readme.broadcast

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.readme.R

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification : BroadcastReceiver()
{
    @SuppressLint("ServiceCast")
    override fun onReceive(context: Context, intent: Intent)
    {
        val title = intent.getStringExtra(titleExtra) ?: "Reminder"
        val message = intent.getStringExtra(messageExtra) ?: "Don't forget about your lesson!"

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.frame_1)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .build()


        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }

}