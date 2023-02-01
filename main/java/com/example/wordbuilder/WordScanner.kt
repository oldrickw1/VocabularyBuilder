package com.example.wordbuilder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.VideoCapture
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import com.example.wordbuilder.databinding.ActivityAddTranslationBinding.inflate
import com.example.wordbuilder.databinding.ActivityMainBinding
import com.example.wordbuilder.databinding.ActivityWordScannerBinding
import java.util.concurrent.ExecutorService

class WordScanner: AppCompatActivity() {
    private lateinit var viewBinding: ActivityWordScannerBinding

    private var imageCapture: ImageCapture? = null
    private var recording: Recording? = null

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityWordScannerBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

    }
}