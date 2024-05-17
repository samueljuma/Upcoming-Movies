package com.samueljuma.upcomingmovies.utils

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.samueljuma.upcomingmovies.workers.FeaturedMovieNotificationWorker
import java.util.concurrent.TimeUnit

fun scheduleFeaturedMovieNotification(context: Context){
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .build()

    val workRequest = PeriodicWorkRequestBuilder<FeaturedMovieNotificationWorker>(
        1, TimeUnit.DAYS
    ) //Once Daily
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        WORK_NAME,
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}