package com.ideasfactory.greenprojectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.ideasfactory.greenprojectapp.databinding.ActivitySplashScreenBinding
import java.lang.Exception

class SplashScreen : AppCompatActivity() {


    lateinit var auth: FirebaseAuth
    lateinit var binding : ActivitySplashScreenBinding
    lateinit var splashLogo : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen)
        FirebaseApp.initializeApp(this)
        splashLogo = binding.splashLogo

       auth = FirebaseAuth.getInstance()

        //This value sleeps the louncher activity for 3 seg and if user is logged, go to mainActivity, if don't , go to Login

        val background = object : Thread(){
            override fun run() {
                try {
                    Thread.sleep(3000)

                    val activityIntent: Intent
                    val currentUser = auth.currentUser
                    // go straight to main if a token is stored
                    activityIntent = if (currentUser != null) {
                        Intent(baseContext, MainActivity::class.java)
                    } else {
                        Intent(baseContext, LogInActivity::class.java)

                    }
                    startActivity(activityIntent)
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()


    }


}
