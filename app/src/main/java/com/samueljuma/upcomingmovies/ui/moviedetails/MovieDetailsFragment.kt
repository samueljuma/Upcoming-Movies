package com.samueljuma.upcomingmovies.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.samueljuma.upcomingmovies.R
import com.samueljuma.upcomingmovies.databinding.FragmentMovieDetailsBinding
import com.samueljuma.upcomingmovies.utils.setDetailsTitle

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    private val viewModel: MovieDetailsViewModel by viewModels()

    private val arguments: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)

        binding.lifecycleOwner = this //very important when using variables from viewmodel in xml

        setDetailsTitle(arguments.movie)

        viewModel.setMovie(arguments.movie)

        viewModel.movie.observe(viewLifecycleOwner){
            binding.movie = it
        }


        return binding.root
    }

}