package ge.nrogava.alarmapp.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import ge.nrogava.alarmapp.R
import java.text.SimpleDateFormat
import java.util.*

class MyBroadcastReceiver() : BroadcastReceiver(){


    override fun onReceive(context: Context?, intent: Intent?) {
        var notificationManager = context?.let { NotificationManagerCompat.from(it) }
        var action = intent?.action
        //= intent?.get
        if (action.equals("Cancel")) {
            val ussreID = intent?.getStringExtra("UserID")
            Toast.makeText(context, "Deleted ID: $ussreID", Toast.LENGTH_SHORT).show()
        }

        if (action.equals("Snooze")) {

            val alarmId = intent?.getStringExtra("alarmId")

            //listener.connectionLost(alarmId)
            Toast.makeText(context, "blaaaaa", Toast.LENGTH_SHORT).show()
        }

        val channelId = "My_Channel_ID"
        createNotificationChannel(channelId, notificationManager)

        // Create an explicit intent for an activity in this app
        val intent = Intent(context, MainActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)


        val deleteIntent = Intent(context, MyBroadcastReceiver::class.java)
        deleteIntent.apply {
            action = "Cancel"
            putExtra("alarmId", "100")
        }
        val deletePendingIntent = PendingIntent.getBroadcast(context, 0, deleteIntent, 0)


        val updateIntent = Intent(context, MyBroadcastReceiver::class.java)
        updateIntent.apply {
            action = "Snooze"
            putExtra("alarmId", "100")
        }
        val updatePendingIntent = PendingIntent.getBroadcast(context, 0, updateIntent, 0)
        val cal = Calendar.getInstance()
        //val time_str = intent.getStringExtra("time")
        val time_str = SimpleDateFormat("HH:mm").format(TIMET)
        //cal.time = SimpleDateFormat("HH:mm").parse(time_str)
        cal.time = TIMET



        val notificationBuilder = context?.let {
            NotificationCompat.Builder(it, channelId)
                .setSmallIcon(R.drawable.alarm_clock)
                .setContentTitle(MainActivity.ALARM_Message)
                .setContentText(MainActivity.ALARM_SET_ON + time_str)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Cancel", deletePendingIntent)
                .addAction(R.mipmap.ic_launcher, "Snooze", updatePendingIntent)
        }


        with(context?.let { NotificationManagerCompat.from(it) }) {
            notificationBuilder?.build()?.let { this?.notify(1, it) }
        }
    }

    private fun createNotificationChannel(
        channelId: String,
        notificationManager: NotificationManagerCompat?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel"
            val channelDescription = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, name, importance)
            channel.apply {
                description = channelDescription
            }

            //val notificationManager =
              //  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }



}