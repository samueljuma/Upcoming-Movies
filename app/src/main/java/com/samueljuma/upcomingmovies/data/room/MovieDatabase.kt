package com.samueljuma.upcomingmovies.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.samueljuma.upcomingmovies.utils.DATABASE_NAME

@Database(entities = [MovieEntity::class], version = 2, exportSchema = false)
abstract class MovieDatabase : RoomDatabase(){

    abstract val movieDao:MovieDao

    companion object{
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}