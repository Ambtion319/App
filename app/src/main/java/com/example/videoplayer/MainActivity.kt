package com.example.videoplayer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : ComponentActivity() {
    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoView = findViewById(R.id.videoView)
        val btnSelectVideo = findViewById<Button>(R.id.btnSelectVideo)

        // إذا تم فتح التطبيق من "Open with"
        val videoUri: Uri? = intent?.data
        if (videoUri != null) {
            playVideo(videoUri)
        }

        // زر لاختيار فيديو من الهاتف
        val pickVideoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedVideoUri: Uri? = result.data?.data
                if (selectedVideoUri != null) {
                    playVideo(selectedVideoUri)
                }
            }
        }

        btnSelectVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            pickVideoLauncher.launch(intent)
        }
    }

    private fun playVideo(videoUri: Uri) {
        videoView.setVideoURI(videoUri)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
    }
}
