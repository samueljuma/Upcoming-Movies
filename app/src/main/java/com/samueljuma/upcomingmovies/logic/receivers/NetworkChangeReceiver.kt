package com.samueljuma.upcomingmovies.logic.receivers

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.samueljuma.upcomingmovies.utils.NetworkUtils

class NetworkChangeReceiver : BroadcastReceiver() {

    private val TAG = this::class.java.simpleName

    override fun onReceive(mContext: Context?, intent: Intent?) {
        try {
            if (mContext != null) {
                val isDisconnected: Boolean = NetworkUtils.isNetworkOffline(mContext)
                when {
                    isDisconnected -> {
                        NetworkUtils.showOfflineNetworkSnack(
                            mContext, (mContext as Activity).findViewById(android.R.id.content)
                        )
                    }

                    else -> {
                        NetworkUtils.dismissOfflineNetworkSnack()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "onReceive Error: ", e)
        }
    }
}