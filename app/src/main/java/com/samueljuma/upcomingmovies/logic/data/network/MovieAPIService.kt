package com.samueljuma.upcomingmovies.logic.data.network

import com.samueljuma.upcomingmovies.logic.data.Movie
import com.samueljuma.upcomingmovies.logic.data.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPIService {

    @GET("movie/upcoming")
    suspend fun getMovies(@Query("api_key") apiKey: String): Response<MovieList>
}