package com.developer.fabian.notificationservice

import android.app.*
import android.content.Intent
import android.os.Handler
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.widget.Toast

class MessageService : IntentService("MessageService") {

    companion object {
        const val CHANNEL_ID = "com.developer.fabian.notificationservice"
        const val MESSAGE_EXTRA = "message"
        const val ID = 1234
    }

    private var handler: Handler? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler = Handler()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        synchronized(this) {
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        val text = intent!!.getStringExtra(MESSAGE_EXTRA)
        showText(text)
    }

    private fun showText(text: String) {
        handler!!.post {
            Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
        }

        val intent = Intent(this, MainActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(this)

        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(intent)

        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val channel = NotificationChannel(CHANNEL_ID, "Notification Service", NotificationManager.IMPORTANCE_HIGH)
        channel.description = "App notification channel"
        channel.setShowBadge(true)

        val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(getString(R.string.app_name))
            setContentText(text)
            setContentIntent(pendingIntent)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setAutoCancel(true)
        }

        val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)
        notificationManagerCompat.notify(ID, notificationBuilder.build())
    }
}
