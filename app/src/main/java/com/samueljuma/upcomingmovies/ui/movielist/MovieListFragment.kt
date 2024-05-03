package com.samueljuma.upcomingmovies.ui.movielist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.samueljuma.upcomingmovies.databinding.FragmentMovieListBinding
import com.samueljuma.upcomingmovies.utils.API_KEY
import com.samueljuma.upcomingmovies.utils.Result
import com.samueljuma.upcomingmovies.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {


    private lateinit var binding: FragmentMovieListBinding

    private val viewModel: MoviesViewModel by viewModels()

    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)

        adapter = MovieListAdapter(MovieClickListener {
            viewModel.onMovieClicked(it)
        })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)

        viewModel.fetchPopularMovies(API_KEY)

        viewModel.movies.observe(viewLifecycleOwner){ result ->
            when(result){
                is Result.Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    val movies = result.data
                    adapter.submitList(movies)
                }
                is Result.Error -> {
                    val error = result.exception
                    Toast.makeText(context, "Error Getting the data", Toast.LENGTH_SHORT).show()
                    Log.i("TAGGY", "Error: $error")
                }
            }
        }

        viewModel.navigateToDetails.observe(viewLifecycleOwner){movie->
            movie?.let {
                this.findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment()
                )
                viewModel.doneNavigatingToDetails()
            }

        }


        return binding.root

    }

}