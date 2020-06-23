package com.ideasfactory.greenprojectapp.fragments


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.ideasfactory.greenprojectapp.LogInActivity
import com.ideasfactory.greenprojectapp.R
import com.ideasfactory.greenprojectapp.databinding.FragmentProfileBinding
import jp.wasabeef.glide.transformations.BlurTransformation
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

    companion object{
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

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
        binding.ivProfilePhoto.setOnClickListener(this)
        setBirthdayEditText()


        profilePhoto.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    //permision denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show pop up to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                }else{
                    //permission already garanted
                    pickImageFromGalery()
                }
            }else{
                pickImageFromGalery()
            }
        }
        return binding.root


    }

    private fun pickImageFromGalery() {
        //INTENT TO PICK IMAGE
        val intent = Intent (Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE->{
                if (grantResults.size>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //permision from popup granted
                    pickImageFromGalery()
                }else{
                    Toast.makeText(requireContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE ){
            //profilePhoto.setImageURI(data?.data)
           Glide.with(this)
                .load(data?.data)
                .circleCrop()
                .into(profilePhoto)

            Glide.with(this)
                .load(data?.data)
                .apply(bitmapTransform(BlurTransformation(400)))
                .into(backgroundProfile)

            //storage -> todo : Ver los métodos de Yacine para completar. pero primero instruirse en lo que es FireStorage.



        }
    }

    override fun onStart() {
        super.onStart()
        val emailUserAuth: String? = FirebaseAuth.getInstance().currentUser!!.email
        userEmail.setHint(emailUserAuth)
        noteListener =  docRef.addSnapshotListener {  documentSnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                //Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            if (documentSnapshot!!.exists()) {
                Log.d(TAG, "DocumentSnapshot data: ${documentSnapshot.data}")
                userName.setText(documentSnapshot.getString("userFirstName"))
                userLastName.setText(documentSnapshot.getString("userLastName"))
                userBirth.setText(documentSnapshot.getString("userBirth"))
                userAddress.setText(documentSnapshot.getString("userAddress"))
                userPostalCode.setText(documentSnapshot.getString("userPostalCode"))
                userAddress.setText(documentSnapshot.getString("userAddress"))
                userCity.setText(documentSnapshot.getString("userCity"))
                userCountry.setText(documentSnapshot.getString("userCountry"))
                userNameProfile.setText(documentSnapshot.getString("userFirstName"))


            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_update_user_information -> {
                //saveUserInformation()
                updateUserInformation()
                loadUserInformation()
                Toast.makeText(requireContext(), "Mise à jour réussie", Toast.LENGTH_SHORT).show()
            }
            R.id.sign_out -> {
                auth.signOut()
                startActivity(Intent(requireContext(), LogInActivity::class.java))
            }

        }
    }


    private fun saveUserInformation(){

        val emailUserAuth: String? = FirebaseAuth.getInstance().currentUser!!.email
        userEmail.setHint(emailUserAuth)
        val userNameInput = userName.text.toString()
        val userLastNameInput = userLastName.text.toString()
        val userBirthInput = userBirth.text.toString()
        val userAddresInput = userAddress.text.toString()
        val userPostalCodeInput = userPostalCode.text.toString()
        val userCityInput = userCity.text.toString()
        val userCountryInput = userCountry.text.toString()
        val userEmail = userEmail.text.toString()
        val documentReference = fbStore.collection("users").document(userId)

        //to fill the document with the user data, whe create a Map <Key, variable> (the key is the name that will appear in fireStore, value is the information user writes)
        var userInformation  = HashMap<String, Any>()
        userInformation.put("userFirstName",userNameInput)
        userInformation.put("userLastName",userLastNameInput)
        userInformation.put("userBirth",userBirthInput)
        userInformation.put("userAddress", userAddresInput)
        userInformation.put ("userPostalCode", userPostalCodeInput)
        userInformation.put ("userCity", userCityInput)
        userInformation.put ("userCountry", userCountryInput)
        userInformation.put("userEmail", emailUserAuth!!)
        documentReference.set(userInformation)

    }

    fun updateUserInformation(){
        val emailUserAuth: String? = FirebaseAuth.getInstance().currentUser!!.email
        userEmail.setHint(emailUserAuth)

        val userNameInput = userName.text.toString()
        val userLastNameInput = userLastName.text.toString()
        val userBirthInput = userBirth.text.toString()
        val userAddresInput = userAddress.text.toString()
        val userPostalCodeInput = userPostalCode.text.toString()
        val userCityInput = userCity.text.toString()
        val userCountryInput = userCountry.text.toString()
        val userEmail = userEmail.text.toString()
        docRef.update("userFirstName",userNameInput)
        docRef.update("userLastName",userLastNameInput)
        docRef.update("userBirth",userBirthInput)
        docRef.update("userAddress",userAddresInput)
        docRef.update("userPostalCode",userPostalCodeInput)
        docRef.update("userCity",userCityInput)
        docRef.update("userCountry",userCountryInput)
        docRef.update("userEmail",emailUserAuth!!)
    }


    private fun loadUserInformation(){
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    userName.setText(document.getString("userFirstName"))
                    userLastName.setText(document.getString("userLastName"))
                    userBirth.setText(document.getString("userBirth"))
                    userAddress.setText(document.getString("userAddress"))
                    userPostalCode.setText(document.getString("userPostalCode"))
                    userAddress.setText(document.getString("userAddress"))
                    userCity.setText(document.getString("userCity"))
                    userCountry.setText(document.getString("userCountry"))
                    userNameProfile.setText(document.getString("userName"))
                    //userEmail.setText(document.getString("userEmail"))

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
