package com.samueljuma.upcomingmovies.ui.mainactivity

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.samueljuma.upcomingmovies.R
import com.samueljuma.upcomingmovies.databinding.ActivityMainBinding
import com.samueljuma.upcomingmovies.utils.PREF_KEY_FIRST_LAUNCH
import com.samueljuma.upcomingmovies.utils.WORK_NAME
import com.samueljuma.upcomingmovies.utils.scheduleFeaturedMovieNotification
import com.samueljuma.upcomingmovies.workers.FeaturedMovieNotificationWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var sharedPreferences: SharedPreferences


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        /**
         * I used Shared Preferences to store the value for isFirstLaunch
         */

        if (isFirstRun()) {
            navController.setGraph(R.navigation.nav_graph)
        } else {
            val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
            navGraph.setStartDestination(R.id.movieListFragment)
            navController.graph = navGraph
        }

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragmentContainerView).navigateUp(appBarConfiguration)
    }


    private fun isFirstRun(): Boolean {
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean(PREF_KEY_FIRST_LAUNCH, true)
        if (isFirstRun) {
            sharedPreferences.edit().putBoolean(PREF_KEY_FIRST_LAUNCH, false).apply()
        }
        return isFirstRun
    }


}