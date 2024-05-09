package com.samueljuma.upcomingmovies.workers

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.samueljuma.upcomingmovies.data.room.MovieDatabase
import com.samueljuma.upcomingmovies.repository.MovieRepository
import com.samueljuma.upcomingmovies.utils.NotificationUtils
import com.samueljuma.upcomingmovies.utils.toMovie
import retrofit2.HttpException

class FeaturedMovieNotificationWorker(
    context: Context,
    params: WorkerParameters,
): CoroutineWorker(context, params) {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun doWork(): Result {

        return try {
            val database = MovieDatabase.getInstance(applicationContext)

            if(NotificationUtils.hasNotificationPermission(applicationContext)){
                Log.i(TAG, "Notification is granted")
//                val featuredMovie = repository.getFeaturedMovie()
                val featuredMovie = database.movieDao.getRandomMovie()

                Log.i(TAG, "Featured movie fetched Successfully")
                // ShowNotification
                NotificationUtils.sendNotification(applicationContext, featuredMovie.toMovie())

                Log.i(TAG, "Success showing Notification")
            }else{

                Log.i(TAG, "It Appears there is no permission to show notification")
            }

            Result.success()

        } catch (e: Exception){
            Log.i(TAG, "Error: $e")
            Result.retry()
        }
    }

    companion object {
        const val TAG = "FeaturedMovieNotificationWorker"
    }
}