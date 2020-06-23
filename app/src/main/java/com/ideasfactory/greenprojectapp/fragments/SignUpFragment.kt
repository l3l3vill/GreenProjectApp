package com.ideasfactory.greenprojectapp.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.ideasfactory.greenprojectapp.MainActivity
import com.ideasfactory.greenprojectapp.model.User

import com.ideasfactory.greenprojectapp.R
import com.ideasfactory.greenprojectapp.databinding.FragmentSignUpBinding
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    val EMAIL_KEY = "email_key"
    lateinit var binding: FragmentSignUpBinding
    val PASSWORD_PATTERN = Pattern.compile("(?=.*[0-9])(?=.*[A-Z])(?=\\\\S+\$).{8,}")

    //views
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var verifyPasword: EditText
    lateinit var signUp: Button
    lateinit var nameUser : EditText
    lateinit var lastNameUser : EditText
    lateinit var progressBar : ProgressBar

    //fireBaseAuth
    private lateinit var auth: FirebaseAuth

    //FirestoreInstanse
    private lateinit var fbStore : FirebaseFirestore
    private lateinit var userId : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        //views
        email = binding.etEmail
        password = binding.etPassword
        verifyPasword = binding.etVerifyPassword
        signUp = binding.btnSignUp
        nameUser = binding.etPrenom
        lastNameUser = binding.etNom
        progressBar = binding.progressSignUp

        auth = FirebaseAuth.getInstance()

        fbStore = FirebaseFirestore.getInstance()


        signUp.setOnClickListener {
            signUpUser()
        }

        if (savedInstanceState != null) {
            savedInstanceState.getString(EMAIL_KEY)
        }

        return binding.root

    }



    // this function verify inputs that user types and if are not correct, notifys the user, if are correc, User is signed up and added to firebase as a new authentified User
    private fun signUpUser(){
        val emailIntput: String = email.text.toString()
        val passwordInput : String = password.text.toString()
        val verifyPasswordInput : String = verifyPasword.text.toString()
        val nameInput : String = nameUser.text.toString()
        val lastNameInput : String = lastNameUser.text.toString()


        if (nameInput.isEmpty()) {
            nameUser.error = "Entrez votre prenom"
            nameUser.requestFocus()
            return
        }

        if (lastNameInput.isEmpty()) {
            lastNameUser.error = "Entrez votre nom"
            lastNameUser.requestFocus()
            return
        }

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
            password.error = "Entrez un mot de passe avec au moins 8 caractères"
            password.requestFocus()
            return
        }

        if (passwordInput!= verifyPasswordInput) {
            verifyPasword.error = "Veuillez vérifier le mot de passe"
            verifyPasword.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(emailIntput, passwordInput)
            .addOnCompleteListener { task ->
                if (task.isSuccessful ) {
                    val user = auth.currentUser
                    val profileUpdates_name : UserProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName(nameInput).build()
                    user?.updateProfile(profileUpdates_name)
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    requireContext(), "Authentification reussie",
                                    Toast.LENGTH_SHORT
                                ).show()

                                //Here we are creating the collection "users". we haven't created yet, so whit this variable we are creating it.
                                //also we are inserted the documment taking de id provided by auth.
                                userId = auth.currentUser!!.uid
                                val documentReference = fbStore.collection("users").document(userId)
                                //to fill the document with the user data, whe create a Map <Key, variable> (the key is the name that will appear in fireStore, value is the information user writes)

                                var user : User = User(nameInput,lastNameInput,"",emailIntput,"","","","")

                   /*             var userInformation  = HashMap<String, Any>()
                                userInformation.put("UserName",nameInput )
                                userInformation.put("UserLastName", lastNameInput)
                                userInformation.put ("UserEmail", emailIntput)*/

                                documentReference.set(user).addOnCompleteListener {
                                    if(task.isSuccessful){
                                        Log.i("SUGNUP","onSuccess: userInformation is created for $userId ")
                                    }
                                }
                                startActivity(Intent(requireContext(), MainActivity::class.java))

                            }
                        }

                } else {
                    Toast.makeText(requireContext(), "Authentification echouee", Toast.LENGTH_SHORT)
                        .show()
                }
            }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val emailIntput: String = email.text.toString()
        outState.putString(EMAIL_KEY, emailIntput )


    }

    override fun onResume() {
        super.onResume()
    }

}
