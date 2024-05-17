package com.samueljuma.upcomingmovies.ui.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.samueljuma.upcomingmovies.R
import com.samueljuma.upcomingmovies.data.WelcomeItem
import com.samueljuma.upcomingmovies.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var adapter: WelcomeItemAdapter
    private lateinit var viewPager2: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)

        adapter = WelcomeItemAdapter()

        viewPager2 = binding.viewPager2

        viewPager2.adapter = adapter

        //Attach dot indicator to viewPager
        binding.indicator.attachTo(viewPager2)

        val listOfWelcomeItems = listOf(
            WelcomeItem(0, R.drawable.welcome, getString(R.string.welcome_message_1)),
            WelcomeItem(1, R.drawable.welcome, getString(R.string.welcome_message_2)),
            WelcomeItem(2, R.drawable.welcome, getString(R.string.welcome_message_3)),
//            WelcomeItem(1, R.drawable.welcome, getString(R.string.welcome_message_1))
        )

        adapter.submitList(listOfWelcomeItems)


        binding.continueBtn.setOnClickListener {
            this.findNavController().navigate(
                WelcomeFragmentDirections.actionWelcomeFragmentToMovieListFragment()
            )
        }

        return binding.root
    }

}