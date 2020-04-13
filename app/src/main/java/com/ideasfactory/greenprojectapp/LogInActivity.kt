package com.ideasfactory.greenprojectapp

import android.media.AudioManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.VideoView
import androidx.databinding.DataBindingUtil
import com.ideasfactory.greenprojectapp.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    lateinit var binding: ActivityLogInBinding
    lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_log_in)

        videoView = binding.initialVideo

        videoView.setVideoPath("android.resource://" + packageName + "/" + R.raw.introvideo)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            videoView.setAudioFocusRequest(AudioManager.AUDIOFOCUS_NONE)
        }

        videoView.start()

        videoView.setOnPreparedListener({ mp -> mp.isLooping = true })

    }

    override fun onPause() {
        super.onPause()
        videoView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoView.start()
    }
}
