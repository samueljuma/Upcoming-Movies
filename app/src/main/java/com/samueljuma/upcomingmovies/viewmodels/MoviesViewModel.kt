package com.samueljuma.upcomingmovies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samueljuma.upcomingmovies.data.Movie
import com.samueljuma.upcomingmovies.repository.MovieRepository
import com.samueljuma.upcomingmovies.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel(){

    private val _movies = MutableLiveData<Result<List<Movie>>>()
    val movies: LiveData<Result<List<Movie>>> = _movies

    fun fetchPopularMovies(apiKey: String){

        viewModelScope.launch {
            /**
             * Be sure to try postValue
             */

            // Set loading state
            _movies.value = Result.Loading

            // Fetch movies from repository
            val data = movieRepository.getUpcomingMovies(apiKey)

            // Update LiveData with result
            _movies.value = data
        }
    }

}