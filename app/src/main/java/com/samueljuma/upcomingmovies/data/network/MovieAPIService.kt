package com.samueljuma.upcomingmovies.data.network

import com.samueljuma.upcomingmovies.data.Movie
import com.samueljuma.upcomingmovies.data.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPIService {

    @GET("movie/upcoming")
    suspend fun getMovies(@Query("api_key") apiKey: String): Response<MovieList>
}