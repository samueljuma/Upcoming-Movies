package com.samueljuma.upcomingmovies

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.samueljuma.upcomingmovies.databinding.ActivityMainBinding
import com.samueljuma.upcomingmovies.utils.API_KEY
import com.samueljuma.upcomingmovies.utils.Result
import com.samueljuma.upcomingmovies.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MoviesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.fetchPopularMovies(API_KEY)

        viewModel.movies.observe(this){ result ->
            when(result){
                is Result.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    val movies = result.data
                    binding.txtView.text = movies[0].title
                    Log.i("TAGGY", "${movies[0]}")
                }
                is Result.Error -> {
                    val error = result.exception
                    Toast.makeText(this, "Error Getting the data", Toast.LENGTH_SHORT).show()
                    Log.i("TAGGY", "Error: $error")
                }
            }
        }


    }
}