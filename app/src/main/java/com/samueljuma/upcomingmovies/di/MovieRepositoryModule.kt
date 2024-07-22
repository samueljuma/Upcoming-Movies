package com.samueljuma.upcomingmovies.di

import com.samueljuma.upcomingmovies.logic.data.network.MovieAPIService
import com.samueljuma.upcomingmovies.logic.data.room.MovieDao
import com.samueljuma.upcomingmovies.logic.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MovieRepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(movieDao: MovieDao, movieAPIService: MovieAPIService): MovieRepository {
        return MovieRepository(movieDao, movieAPIService)
    }
}