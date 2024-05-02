package com.samueljuma.upcomingmovies.di

import com.samueljuma.upcomingmovies.data.network.MovieAPIService
import com.samueljuma.upcomingmovies.utils.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi{
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    @Singleton
    @Provides
    fun provideMovieAPIService(retrofit: Retrofit): MovieAPIService{
        return retrofit.create(MovieAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(moshi: Moshi): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}