package com.samueljuma.upcomingmovies.repository

import com.samueljuma.upcomingmovies.data.Movie
import com.samueljuma.upcomingmovies.data.network.RetrofitClient
import com.samueljuma.upcomingmovies.utils.Result

class MovieRepository() {

    suspend fun getUpcomingMovies(apiKey: String): Result<List<Movie>> {
        return try {
            Result.Loading
            val response = RetrofitClient.movieAPIService.getMovies(apiKey)
            if (response.isSuccessful) {
                val data = response.body()
                // Use the safe call operator and provide a default empty list if data is null
                val movies = data?.movies ?: emptyList()
                Result.Success(movies)
            } else {
                Result.Error(Exception("Failed to fetch movies"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


}