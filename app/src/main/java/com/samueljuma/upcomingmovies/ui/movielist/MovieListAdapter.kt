package com.samueljuma.upcomingmovies.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samueljuma.upcomingmovies.logic.data.Movie
import com.samueljuma.upcomingmovies.databinding.MovieItemLayoutBinding

class MovieListAdapter(val clickListener: MovieClickListener): ListAdapter<Movie, MovieListAdapter.ViewHolder> (MovieDiffCallBack()){

    class ViewHolder(val binding:MovieItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, clickListener: MovieClickListener){
            binding.movie = movie
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!,clickListener)
    }

}


class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}

class MovieClickListener (val clickListener: (movie: Movie) -> Unit){
    fun onClick(movie: Movie) = clickListener(movie)
}