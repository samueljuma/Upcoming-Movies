package com.samueljuma.upcomingmovies.ui.notificationdetails

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.samueljuma.upcomingmovies.ui.mainactivity.MainActivity
import com.samueljuma.upcomingmovies.R
import com.samueljuma.upcomingmovies.data.Movie
import com.samueljuma.upcomingmovies.databinding.ActivityMovieDetailsNotificationBinding

class MovieDetailsNotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsNotificationBinding

    private val viewModel: NotificationDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details_notification)


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        if (intent?.extras != null) {
            val movie = intent.getParcelableExtra<Movie>("movie") // Ask mentor
            viewModel.setFeaturedMovie(movie)
        }

        viewModel.featuredMovie.observe(this) {
            it?.let {
                binding.movie = it
            }
        }


        /**
         * Go to HomeScreen onBackPressed
         */
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                navigateToHomeScreen()
            }
        }
        onBackPressedDispatcher.addCallback(this,callback)

        // Cancel the notification once the activity is opened
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(0)
    }

    private fun navigateToHomeScreen() {
        val homeIntent = Intent(this, MainActivity::class.java)
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(homeIntent)
        finish()
    }


}