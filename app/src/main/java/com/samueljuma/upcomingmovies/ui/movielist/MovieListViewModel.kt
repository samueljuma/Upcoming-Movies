package com.samueljuma.upcomingmovies.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samueljuma.upcomingmovies.data.Movie
import com.samueljuma.upcomingmovies.repository.MovieRepository
import com.samueljuma.upcomingmovies.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {
    private val _movies = MutableLiveData<Result<List<Movie>>>()
    val movies: LiveData<Result<List<Movie>>> = _movies

    fun fetchUpcomingMovies(apiKey: String){

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

    fun refreshUpcomingMovies(apiKey: String){
        viewModelScope.launch {

            // Set loading state
            _movies.value = Result.Loading

            // Fetch movies from repository
            val data = movieRepository.refreshUpcomingMovies(apiKey)

            // Update LiveData with result
            _movies.value = data
        }
    }

    private  val _navigateToDetails = MutableLiveData<Movie?>()
    val navigateToDetails: LiveData<Movie?> = _navigateToDetails
    fun onMovieClicked(movie: Movie){
        _navigateToDetails.value = movie
    }

    fun doneNavigatingToDetails(){
        _navigateToDetails.value = null
    }

    private val _featuredMovie = MutableLiveData<Movie?>()
    val featuredMovie: LiveData<Movie?> = _featuredMovie

    fun loadFeaturedMovie() {
        viewModelScope.launch {
            _featuredMovie.value = movieRepository.getFeaturedMovie()
        }
    }


}