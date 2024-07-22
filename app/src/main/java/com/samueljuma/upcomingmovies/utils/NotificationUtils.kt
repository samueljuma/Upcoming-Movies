package com.samueljuma.upcomingmovies.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.samueljuma.upcomingmovies.ui.notificationdetails.MovieDetailsNotificationActivity
import com.samueljuma.upcomingmovies.R
import com.samueljuma.upcomingmovies.logic.data.Movie

object NotificationUtils  {

    private const val CHANNEL_ID = "MovieChannel"
    private const val CHANNEL_NAME = "Movie Notification Channel"
    private const val NOTIFICATION_ID = 1


    private fun createNotificationChannel(context: Context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(context: Context, movie: Movie){
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //Create Notification Channel
        createNotificationChannel(context)

        val intent = Intent(context, MovieDetailsNotificationActivity::class.java).apply {
            putExtra("movie", movie)
        }

        val stackBuilder = TaskStackBuilder.create(context).apply {
            addNextIntent(intent)
        }

        val pendingIntent = stackBuilder.getPendingIntent(
            0,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        val action = NotificationCompat.Action.Builder(
            R.drawable.movie_icon,
            "View Details",
            pendingIntent
        ).build()

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.movie_icon)
            .setContentTitle(context.getString(R.string.featured_movie_text))
            .setContentText("${movie.title} : ${movie.overview}")
            .setContentIntent(pendingIntent)
            .addAction(action)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)


    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun hasNotificationPermission(context: Context): Boolean{
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun requestNotificationPermission(requestPermissionLauncher: ActivityResultLauncher<String>, view: View){
        Snackbar.make(
            view,
            "Notifications are required to receive daily movie notifications. Do you want to enable them?",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("See Options") {
            // Launch the permission request if the user chooses to enable notifications
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }.show()
    }



}

