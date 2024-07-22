package com.samueljuma.upcomingmovies.logic.data

import com.squareup.moshi.Json

class MovieList (
    @Json(name = "results") val movies: List<Movie>
)
