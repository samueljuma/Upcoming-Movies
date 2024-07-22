package com.samueljuma.upcomingmovies.ui.base

import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.samueljuma.upcomingmovies.logic.receivers.NetworkChangeReceiver
import com.samueljuma.upcomingmovies.ui.notificationdetails.NotificationDetailsViewModel

abstract class BaseActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    lateinit var sharedPreferences: SharedPreferences
    private val networkReceiver = NetworkChangeReceiver()
    val viewModel: NotificationDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initVariables()
    }

    private fun initVariables() {
        try {
            sharedPreferences = getSharedPreferences(
                "my_preferences", Context.MODE_PRIVATE
            )
        } catch (e: Exception) {
            Log.e(TAG, "initVariables: ", e)
        }
    }

    override fun onResume() {
        try {
            super.onResume()
            registerReceiver(
                networkReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        } catch (e: Exception) {
            Log.e(TAG, "onResume: ", e)
        }
    }

    override fun onPause() {
        try {
            super.onPause()
            unregisterReceiver(networkReceiver)
        } catch (e: Exception) {
            Log.e(TAG, "onPause: ", e)
        }
    }
}