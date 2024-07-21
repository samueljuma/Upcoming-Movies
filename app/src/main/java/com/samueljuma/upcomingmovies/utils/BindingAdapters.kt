package com.samueljuma.upcomingmovies.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.samueljuma.upcomingmovies.R
import com.samueljuma.upcomingmovies.logic.data.Movie

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, movie: Movie?){
    movie?.let {
        Glide.with(imageView.context)
//            .load("https://image.tmdb.org/t/p//w300_and_h300_bestv2"+it.poster_path)
            .load("https://image.tmdb.org/t/p/w342"+it.poster_path)
            .apply (
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imageView)
    }

}
@BindingAdapter("posterImageUrl")
fun loadPoster(imageView: ImageView, movie: Movie?){
    movie?.let {
        Glide.with(imageView.context)
//            .load("https://image.tmdb.org/t/p//w300_and_h300_bestv2"+it.poster_path)
            .load("https://image.tmdb.org/t/p/w500"+it.poster_path)
            .apply (
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imageView)
    }

}