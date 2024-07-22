package com.samueljuma.upcomingmovies.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.samueljuma.upcomingmovies.R

object NetworkUtils {

    private val TAG = this::class.java.simpleName
    private lateinit var snackBar: Snackbar

    fun isNetworkOffline(context: Context) : Boolean {
        return try {
            val connectivityManager = context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            run {
                val network = connectivityManager.activeNetwork ?: return true
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return true
                !networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
        } catch (e: Exception) {
            Log.e(TAG, "isNetworkOffline: ", e)
            false
        }
    }

    fun showOfflineNetworkSnack(mContext: Context, parentView: View) {
        try {
            snackBar = Snackbar.make(
                parentView,
                mContext.getString(R.string.oops_your_internet_is_off),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(mContext.getString(R.string.turn_on)) {
                mContext.startActivity(
                    Intent(Settings.ACTION_WIFI_SETTINGS)
                ) // Navigate to settings
            }
            snackBar.show()
        } catch (e: Exception) {
            Log.e(TAG, "offlineChecker Error: ", e)
        }
    }

    fun dismissOfflineNetworkSnack() {
        try {
            when {
                ::snackBar.isInitialized -> {
                    snackBar.dismiss()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "dismissOfflineSnack Error: ", e)
        }
    }
}