package com.ideasfactory.greenprojectapp.Fragments


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.ideasfactory.greenprojectapp.LogInActivity
import com.ideasfactory.greenprojectapp.MainActivity
import com.ideasfactory.greenprojectapp.R
import com.ideasfactory.greenprojectapp.databinding.FragmentProfileBinding
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), View.OnClickListener {

    lateinit var binding : FragmentProfileBinding
    lateinit var backgroundProfile: ImageView
    lateinit var profilePhoto : ImageView
    lateinit var signOut : ImageView
    lateinit var userName : EditText
    lateinit var userLastName : EditText
    lateinit var userEmail : TextView
    lateinit var userBirth : EditText
    lateinit var userAddress: EditText
    lateinit var userPostalCode : EditText
    lateinit var userCity : EditText
    lateinit var userCountry : EditText
    lateinit var userNameProfile : TextView
    lateinit var buttonUpdate : Button
    lateinit var userId : String
    lateinit var noteListener : ListenerRegistration


    //FirestoreInstanse
    private lateinit var fbStore : FirebaseFirestore
    lateinit var docRef : DocumentReference
    //fireBaseAuth
    private lateinit var auth: FirebaseAuth

    private val TAG = "PROFILEFRAGMENT"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)

        backgroundProfile = binding.ivProfileBackground
        profilePhoto = binding.ivProfilePhoto
        signOut = binding.signOut
        userName = binding.tName
        userLastName = binding.tLastName
        userEmail = binding.tEmail
        userBirth = binding.tBirth
        userAddress = binding.tAddress
        userPostalCode = binding.tPostaleCode
        userCity = binding.tCity
        userCountry = binding.tCountry
        userNameProfile = binding.nameProfile
        buttonUpdate = binding.btnUpdateUserInformation
        auth = FirebaseAuth.getInstance()
        fbStore = FirebaseFirestore.getInstance()
        userId = auth.currentUser!!.uid
        docRef = fbStore.collection("users").document(userId)
        binding.btnUpdateUserInformation.setOnClickListener(this)
        binding.signOut.setOnClickListener(this)
        setBirthdayEditText()

        val emailUserAuth: String? = FirebaseAuth.getInstance().currentUser!!.email


        //todo -> when image profile is taken from the user or fb or google, set URL inside glide to upload. don't delete this code
        /*---------------------------DON'T DELETE-------------------------------------------------------------------------------
        Glide.with(this)
            .load("https://avatars1.githubusercontent.com/u/42851409?s=400&u=e985d0bce8c916727f92b47467128d504f433220&v=4")
            .circleCrop()
        .into(profilePhoto)


        Glide.with(this)
            .load("https://avatars1.githubusercontent.com/u/42851409?s=400&u=e985d0bce8c916727f92b47467128d504f433220&v=4")
            .apply(bitmapTransform(BlurTransformation(400)))
            .into(backgroundProfile)
-----------------------------------------DON'T DELETE--------------------------------------------------------------------------*/

        userEmail.setHint(emailUserAuth)

        loadUserInformation()
        return binding.root


    }

    override fun onStart() {
        super.onStart()
        noteListener =  docRef.addSnapshotListener {  documentSnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                //Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            if (documentSnapshot!!.exists()) {
                Log.d(TAG, "DocumentSnapshot data: ${documentSnapshot.data}")
                userName.setText(documentSnapshot.getString("UserName"))
                userLastName.setText(documentSnapshot.getString("UserLastName"))
                userBirth.setText(documentSnapshot.getString("UserBirth"))
                userAddress.setText(documentSnapshot.getString("UserAddress"))
                userPostalCode.setText(documentSnapshot.getString("UserPostalCode"))
                userAddress.setText(documentSnapshot.getString("UserAddress"))
                userCity.setText(documentSnapshot.getString("UserCity"))
                userCountry.setText(documentSnapshot.getString("UserCountry"))
                userNameProfile.setText(documentSnapshot.getString("UserName"))
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_update_user_information -> {
                saveUserInformation()
                updateUserInformation()
                loadUserInformation()
            }
            R.id.sign_out -> {
                auth.signOut()
                startActivity(Intent(requireContext(), LogInActivity::class.java))
            }

        }
    }


    private fun saveUserInformation(){

        val userNameInput = userName.text.toString()
        val userLastNameInput = userLastName.text.toString()
        val userBirthInput = userBirth.text.toString()
        val userAddresInput = userAddress.text.toString()
        val userPostalCodeInput = userPostalCode.text.toString()
        val userCityInput = userCity.text.toString()
        val userCountryInput = userCountry.text.toString()
        val documentReference = fbStore.collection("users").document(userId)
        //to fill the document with the user data, whe create a Map <Key, variable> (the key is the name that will appear in fireStore, value is the information user writes)
        var userInformation  = HashMap<String, Any>()
        userInformation.put("UserName",userNameInput)
        userInformation.put("UserLastName",userLastNameInput)
        userInformation.put("UserBirth",userBirthInput)
        userInformation.put("UserAddress", userAddresInput)
        userInformation.put ("UserPostalCode", userPostalCodeInput)
        userInformation.put ("UserCity", userCityInput)
        userInformation.put ("UserCountry", userCountryInput)
        documentReference.set(userInformation)

    }

    fun updateUserInformation(){
        val userNameInput = userName.text.toString()
        val userLastNameInput = userLastName.text.toString()
        val userBirthInput = userBirth.text.toString()
        val userAddresInput = userAddress.text.toString()
        val userPostalCodeInput = userPostalCode.text.toString()
        val userCityInput = userCity.text.toString()
        val userCountryInput = userCountry.text.toString()
        docRef.update("UserName",userNameInput)
        docRef.update("UserLastName",userLastNameInput)
        docRef.update("UserBirth",userBirthInput)
        docRef.update("UserAddress",userAddresInput)
        docRef.update("UserPostalCode",userPostalCodeInput)
        docRef.update("UserCity",userCityInput)
        docRef.update("UserCountry",userCountryInput)
    }


    private fun loadUserInformation(){
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    userName.setText(document.getString("UserName"))
                    userLastName.setText(document.getString("UserLastName"))
                    userBirth.setText(document.getString("UserBirth"))
                    userAddress.setText(document.getString("UserAddress"))
                    userPostalCode.setText(document.getString("UserPostalCode"))
                    userAddress.setText(document.getString("UserAddress"))
                    userCity.setText(document.getString("UserCity"))
                    userCountry.setText(document.getString("UserCountry"))
                    userNameProfile.setText(document.getString("UserName"))

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun setBirthdayEditText() {

        userBirth.addTextChangedListener(object : TextWatcher {

            private var current = ""
            private val ddmmyyyy = "JJMMAAAA"
            private val cal = Calendar.getInstance()

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() != current) {
                    var clean = p0.toString().replace("[^\\d.]|\\.".toRegex(), "")
                    val cleanC = current.replace("[^\\d.]|\\.", "")

                    val cl = clean.length
                    var sel = cl
                    var i = 2
                    while (i <= cl && i < 6) {
                        sel++
                        i += 2
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean == cleanC) sel--

                    if (clean.length < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length)
                    } else {


                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        var day = Integer.parseInt(clean.substring(0, 2))
                        var mon = Integer.parseInt(clean.substring(2, 4))
                        var year = Integer.parseInt(clean.substring(4, 8))
                        val c = Calendar.getInstance()
                        var currentYear = c[Calendar.YEAR]
                        mon = if (mon < 1) 1 else if (mon > 12) 12 else mon
                        cal.set(Calendar.MONTH, mon - 1)
                        year = if (year < 1900) 1900 else if (year > currentYear) currentYear else year
                        cal.set(Calendar.YEAR, year)
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = if (day > cal.getActualMaximum(Calendar.DATE)) cal.getActualMaximum(
                            Calendar.DATE
                        ) else day
                        clean = String.format("%02d%02d%02d", day, mon, year)
                    }

                    clean = String.format(
                        "%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8)
                    )

                    sel = if (sel < 0) 0 else sel
                    current = clean
                    userBirth.setText(current)
                    userBirth.setSelection(if (sel < current.count()) sel else current.count())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable) {

            }
        })

    }

    override fun onStop() {
        super.onStop()
        noteListener.remove()
    }
}
