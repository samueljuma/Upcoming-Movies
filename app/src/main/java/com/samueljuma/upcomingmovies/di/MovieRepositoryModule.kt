package com.samueljuma.upcomingmovies.di

import com.samueljuma.upcomingmovies.repository.MovieRepository
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
    fun provideMovieRepository(): MovieRepository{
        return MovieRepository()
    }
}