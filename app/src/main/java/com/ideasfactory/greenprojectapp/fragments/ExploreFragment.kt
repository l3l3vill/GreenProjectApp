package com.ideasfactory.greenprojectapp.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController

import com.ideasfactory.greenprojectapp.R
import com.ideasfactory.greenprojectapp.databinding.FragmentExploreBinding

/**
 * A simple [Fragment] subclass.
 */
class ExploreFragment : Fragment() {

    lateinit var binding: FragmentExploreBinding
    lateinit var fruits : ImageView
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false)
        fruits = binding.ivFruits

        fruits.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_exploreFragment_to_fruitsFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }


}
