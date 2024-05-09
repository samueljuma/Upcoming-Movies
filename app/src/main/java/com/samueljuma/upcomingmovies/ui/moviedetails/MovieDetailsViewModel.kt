package com.samueljuma.upcomingmovies.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samueljuma.upcomingmovies.data.Movie

class MovieDetailsViewModel: ViewModel() {
    private val _movie = MutableLiveData<Movie>()
    val movie : LiveData<Movie> = _movie

    fun setMovie(movie: Movie){
        _movie.value = movie
    }
}