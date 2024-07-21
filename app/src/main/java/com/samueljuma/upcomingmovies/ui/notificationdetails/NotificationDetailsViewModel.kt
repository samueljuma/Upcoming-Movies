package com.samueljuma.upcomingmovies.ui.notificationdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samueljuma.upcomingmovies.logic.data.Movie

class NotificationDetailsViewModel: ViewModel() {

    private val _featuredMovie = MutableLiveData<Movie?>()

    val featuredMovie : LiveData<Movie?> =_featuredMovie

    fun setFeaturedMovie(movie: Movie?){
        _featuredMovie.value = movie
    }
}