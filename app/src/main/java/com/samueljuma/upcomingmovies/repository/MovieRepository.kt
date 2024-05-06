package com.samueljuma.upcomingmovies.repository

import com.samueljuma.upcomingmovies.data.Movie
import com.samueljuma.upcomingmovies.data.network.MovieAPIService
import com.samueljuma.upcomingmovies.data.network.RetrofitClient
import com.samueljuma.upcomingmovies.data.room.MovieDao
import com.samueljuma.upcomingmovies.data.room.MovieEntity
import com.samueljuma.upcomingmovies.utils.Result

class MovieRepository(
    private val movieDao: MovieDao,
    private val movieAPIService: MovieAPIService
) {

    suspend fun getUpcomingMovies(apiKey: String): Result<List<Movie>> {
        return try {
            Result.Loading

            val cachedMovies = movieDao.getAllMovies().map { movieEntity ->
                Movie(
                    movieEntity.id,
                    movieEntity.originalLanguage,
                    movieEntity.originalTitle,
                    movieEntity.overview,
                    movieEntity.posterPath,
                    movieEntity.releaseDate,
                    movieEntity.title
                )
            }
            if (cachedMovies.isNotEmpty()) {
                Result.Success(cachedMovies)
            } else {
                val response = movieAPIService.getMovies(apiKey)
                if (response.isSuccessful) {
                    val data = response.body()
                    // Use the safe call operator and provide a default empty list if data is null
                    val movies = data?.movies ?: emptyList()

                    //Insert Movies to DB
                    movieDao.insertMovies(movies.map { movie ->
                        MovieEntity(
                            movie.id,
                            movie.original_language,
                            movie.original_title,
                            movie.overview,
                            movie.poster_path,
                            movie.release_date,
                            movie.title
                        )
                    })
                    Result.Success(movies)
                } else {
                    Result.Error(Exception("Failed to fetch movies"))
                }
            }

        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun refreshUpcomingMovies(apiKey: String): Result<List<Movie>> {
        return try {
            //Get Movies from API
            val response = movieAPIService.getMovies(apiKey)
            if (response.isSuccessful) {
                val data = response.body()
                val movies = data?.movies ?: emptyList()

                // Delete Movies from DB
                movieDao.clearAllMovies()

                //Insert New List int DB
                movieDao.insertMovies(movies.map { movie ->
                    MovieEntity(
                        movie.id,
                        movie.original_language,
                        movie.original_title,
                        movie.overview,
                        movie.poster_path,
                        movie.release_date,
                        movie.title
                    )
                })
                Result.Success(movies)
            } else {
                Result.Error(Exception("Failed to fetch movies"))
            }


        } catch (e: Exception) {
            Result.Error(e)
        }
    }


}