package com.ideasfactory.greenprojectapp.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.ideasfactory.greenprojectapp.R
import com.ideasfactory.greenprojectapp.databinding.FragmentLogInBinding

/**
 * A simple [Fragment] subclass.
 */
class LogInFragment : Fragment() {

    lateinit var binding : FragmentLogInBinding
    lateinit var signUp : Button
    lateinit var signIn : Button
    lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_log_in, container, false)


        signIn = binding.btnLogin
        signUp = binding.btnSignUp

        signUp.setOnClickListener {
            navController.navigate(R.id.action_logInFragment_to_signUpFragment)
        }

        signIn.setOnClickListener {
            navController.navigate(R.id.action_logInFragment_to_signInFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }



}
