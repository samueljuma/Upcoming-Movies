package com.samueljuma.upcomingmovies

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
import com.samueljuma.upcomingmovies.databinding.ActivityMainBinding
import com.samueljuma.upcomingmovies.utils.API_KEY
import com.samueljuma.upcomingmovies.utils.NotificationUtils.requestNotificationPermission
import com.samueljuma.upcomingmovies.utils.Result
import com.samueljuma.upcomingmovies.utils.WORK_NAME
import com.samueljuma.upcomingmovies.viewmodels.MoviesViewModel
import com.samueljuma.upcomingmovies.workers.FeaturedMovieNotificationWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    //I opted to request permission from Movie Details Fragment

//    private val requestPermissionLauncher: ActivityResultLauncher<String> by lazy {
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ){isGranted ->
//            if(isGranted){
//               scheduleFeaturedMovieNotification()
//            }else{
//                Toast.makeText(applicationContext,"The App needs Notification to show show Featured Movie of the day", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }


//    private val permissionReceiver = object : BroadcastReceiver(){
//
//        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//        override fun onReceive(context: Context, intent: Intent?) {
//            if (intent?.action == FeaturedMovieNotificationWorker.ACTION_PERMISSION_NOT_GRANTED){
//                requestNotificationPermission(requestPermissionLauncher)
//            }
//
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

//
//        //Register Receiver
//        val filter = IntentFilter(FeaturedMovieNotificationWorker.ACTION_PERMISSION_NOT_GRANTED)
//        registerReceiver(permissionReceiver, filter, RECEIVER_NOT_EXPORTED)

        //Schedule Notification when Activity is created
        scheduleFeaturedMovieNotification()

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragmentContainerView).navigateUp(appBarConfiguration)
    }

    /**
     * Later try to move this to utils
     */
    private fun scheduleFeaturedMovieNotification(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<FeaturedMovieNotificationWorker>(
            10, TimeUnit.SECONDS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        unregisterReceiver(permissionReceiver)
//    }
}