package com.samueljuma.upcomingmovies.ui.error

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.samueljuma.upcomingmovies.R
import com.samueljuma.upcomingmovies.databinding.FragmentErrorBinding
import com.samueljuma.upcomingmovies.databinding.FragmentMovieDetailsBinding
import com.samueljuma.upcomingmovies.utils.setDisplayHomeAsEnabled

class ErrorFragment : Fragment() {

    private lateinit var binding: FragmentErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentErrorBinding.inflate(layoutInflater, container,false)

        setDisplayHomeAsEnabled(false)

        binding.retryBtn.setOnClickListener{
            this.findNavController().navigate(
                ErrorFragmentDirections.actionErrorFragmentToMovieListFragment()
            )
        }
        return binding.root
    }

}