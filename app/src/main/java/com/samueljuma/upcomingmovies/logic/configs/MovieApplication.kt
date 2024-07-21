package com.samueljuma.upcomingmovies.logic.configs

import android.app.Application
import com.samueljuma.upcomingmovies.utils.scheduleFeaturedMovieNotification
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class MovieApplication : Application(){

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    /**
     * This helps noy to block the main thread and for faster start up
     */
    private fun delayedInit() = applicationScope.launch {
        scheduleFeaturedMovieNotification(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

}