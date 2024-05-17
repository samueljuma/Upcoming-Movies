package com.samueljuma.upcomingmovies.ui.movielist

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.samueljuma.upcomingmovies.R
import com.samueljuma.upcomingmovies.data.Movie
import com.samueljuma.upcomingmovies.databinding.FragmentMovieListBinding
import com.samueljuma.upcomingmovies.utils.API_KEY
import com.samueljuma.upcomingmovies.utils.NotificationUtils
import com.samueljuma.upcomingmovies.utils.Result
import com.samueljuma.upcomingmovies.utils.setDisplayHomeAsEnabled
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {


    private lateinit var binding: FragmentMovieListBinding

    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var adapter: MovieListAdapter

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)

        //Hide Up Button
        setDisplayHomeAsEnabled(false)

//        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)


        /**
         * Initialize request Permission Launcher
         */
        requestPermissionLauncher = registerForActivityResult(RequestPermission()){ isGranted->
            if(isGranted){
                //TODO
            }
            else{
                Toast.makeText(context,"The App needs Notification to show show Movie of the day", Toast.LENGTH_SHORT).show()
            }
        }


        /**
         * inflate Handle menu items onClick events
         */

        val menuProvider = object: MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.movie_list_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId){
                    R.id.menu_refresh -> {
                        try{
                            viewModel.refreshUpcomingMovies(API_KEY)
//                            Toast.makeText(context, "Movie data is now up to date", Toast.LENGTH_SHORT).show()
                        }catch(e:ExceptionInInitializerError){
                            Toast.makeText(context, "Failed to Refresh", Toast.LENGTH_SHORT).show()
                        }


                        true
                    }

                    else -> {
                        false
                    }
                }
            }

        }

        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)

        //Initialize adapter
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

                    //set Featured Movie
                    viewModel.loadFeaturedMovie()

                    binding.progressBar3.isVisible = false

                    //RequestNotification Permission
                    requestNotificationPermission(requireContext(), binding.root)

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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun requestNotificationPermission(context: Context, view: View){
        if(!NotificationUtils.hasNotificationPermission(context)){
            NotificationUtils.requestNotificationPermission(requestPermissionLauncher,view)
        }
    }


}