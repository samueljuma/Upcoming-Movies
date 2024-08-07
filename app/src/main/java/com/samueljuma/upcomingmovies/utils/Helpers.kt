package com.samueljuma.upcomingmovies.utils

import com.samueljuma.upcomingmovies.logic.data.Movie
import com.samueljuma.upcomingmovies.logic.data.room.MovieEntity

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        original_language = originalLanguage,
        original_title = originalTitle,
        overview = overview,
        poster_path = posterPath,
        release_date = releaseDate,
        title = title
    )
}