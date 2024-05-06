package com.samueljuma.upcomingmovies.ui.movielist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.samueljuma.upcomingmovies.R
import com.samueljuma.upcomingmovies.databinding.FragmentMovieListBinding
import com.samueljuma.upcomingmovies.utils.API_KEY
import com.samueljuma.upcomingmovies.utils.Result
import com.samueljuma.upcomingmovies.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {


    private lateinit var binding: FragmentMovieListBinding

    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)

        //Hide Up Button
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val menuProvider = object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.movie_list_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId){
                    R.id.menu_refresh -> {
                        Toast.makeText(context, "Movie data is now up to date", Toast.LENGTH_SHORT).show()
                        viewModel.refreshUpcomingMovies(API_KEY)
                        true
                    }

                    else -> {
                        false
                    }
                }
            }

        }

        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)

        adapter = MovieListAdapter(MovieClickListener {
            viewModel.onMovieClicked(it)
        })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)

        binding.lifecycleOwner = this

        viewModel.fetchUpcomingMovies(API_KEY)

        viewModel.movies.observe(viewLifecycleOwner){ result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar3.isVisible = true
                }
                is Result.Success -> {
                    val movies = result.data
                    adapter.submitList(movies)
                    binding.progressBar3.isVisible = false
                }
                is Result.Error -> {
                    val error = result.exception
                    Toast.makeText(context, "Error Getting the data", Toast.LENGTH_SHORT).show()
                    Log.i("TAGGY", "Error: $error")
                    this.findNavController().navigate(
                        MovieListFragmentDirections.actionMovieListFragmentToErrorFragment()
                    )
                }

                else -> {Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()}
            }
        }

        viewModel.navigateToDetails.observe(viewLifecycleOwner){movie->
            movie?.let {
                this.findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movie)
                )
                viewModel.doneNavigatingToDetails()
            }

        }

        return binding.root

    }

}