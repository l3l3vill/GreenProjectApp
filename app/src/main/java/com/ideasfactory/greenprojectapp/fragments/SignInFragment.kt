package com.ideasfactory.greenprojectapp.fragments


import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.ideasfactory.greenprojectapp.MainActivity

import com.ideasfactory.greenprojectapp.R
import com.ideasfactory.greenprojectapp.databinding.FragmentSignInBinding

/**
 * A simple [Fragment] subclass.
 */
class SignInFragment : Fragment() {
    //firebase
    private lateinit var auth: FirebaseAuth
    //views
    lateinit var binding : FragmentSignInBinding
    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var signIn: Button
    lateinit var forgotPassword : Button
    lateinit var progressBar: ProgressBar



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_in, container, false )
        auth = FirebaseAuth.getInstance()
        email = binding.etEmail
        password = binding.etPassword
        signIn = binding.btnSignIn
        forgotPassword = binding.forgotPasword
        progressBar = binding.progressSignIn

        signIn.setOnClickListener {
            sigInUser()
        }

        forgotPassword.setOnClickListener {
            alertDialogForgotPassword()
        }

        return binding.root

    }


    private fun sigInUser(){
        val emailIntput: String = email.text.toString()
        val passwordInput : String = password.text.toString()




        if (emailIntput.isEmpty()) {
            email.error = "Entrez votre email"
            email.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailIntput).matches()) {
            email.error = "Entrez un email valide"
            email.requestFocus()
            return

        }

        if(passwordInput.isEmpty() || passwordInput.length < 8 ){
            password.error = "Entrez votre mot de passe"
            password.requestFocus()
            return
        }

        progressBar.visibility = VISIBLE
        auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(requireContext(), "Authentification reussie",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), MainActivity::class.java))

                }else{
                    Toast.makeText(requireContext(), "Authentification echouee", Toast.LENGTH_SHORT).show()

                }
            }


    }

    private fun alertDialogForgotPassword(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("J'ai oublié mon mot de passe")
        val view = layoutInflater.inflate(R.layout.dialog_forgot_pasword,null)
        val emailInput = view.findViewById<EditText>(R.id.et_username)
        builder.setView(view)
        builder.setPositiveButton("Valider", DialogInterface.OnClickListener { _, _ ->
            forgotPassword(emailInput)
        })
        builder.setNegativeButton("Fermer", DialogInterface.OnClickListener { _, _ ->

        })
        builder.show()
    }

    private fun forgotPassword(email : EditText){
        val emailIntput: String = email.text.toString()
        if(emailIntput.isEmpty()){
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailIntput).matches()){
            return
        }

        auth.sendPasswordResetEmail(emailIntput)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email envoyé")
                    Toast.makeText(requireContext(), "Email envoyé", Toast.LENGTH_SHORT).show();
                }
            }


    }
}
