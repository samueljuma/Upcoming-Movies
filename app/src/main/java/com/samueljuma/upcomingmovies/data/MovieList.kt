package com.samueljuma.upcomingmovies.data

import com.squareup.moshi.Json

class MovieList (
    @Json(name = "results") val movies: List<Movie>
)
