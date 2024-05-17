package com.samueljuma.upcomingmovies.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY releaseDate DESC ")
    suspend fun getAllMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()

    @Query("SELECT * FROM movies ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomMovie(): MovieEntity

}