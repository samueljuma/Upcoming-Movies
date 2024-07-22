package com.samueljuma.upcomingmovies.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.samueljuma.upcomingmovies.logic.data.Movie

fun Fragment.setDetailsTitle(movie: Movie){
    if(activity is AppCompatActivity){
        (activity as AppCompatActivity).supportActionBar?.title = movie.title
    }
}

fun Fragment.setDisplayHomeAsEnabled(boolean: Boolean){
    if(activity is AppCompatActivity){
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(boolean)
    }
}