package com.example.wordbuilder

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.wordbuilder.databinding.ActivityWordScannerBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.log


//TODO: Change this code so that the text in the captured photo will be extracted
//TODO: Once I get the text, go to new activity (or dialog). Show the extracted words. User can decide to scan again, or to add the words to library (use translation API)
class WordScanner: AppCompatActivity() {
    // this datatype binds my layout views to this variable (still needs to be done), so that I can access them more easily
    // in order for this to work, I need to include "buildFeatures {viewBinding true}" in the build.gradle (module) file
    private lateinit var viewBinding: ActivityWordScannerBinding

    // ImageCapture is a class that offers functionality of taking pictures. takePicture() is one of its methods
    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null

    // File Elements:
    private lateinit var outputDirectory: File

    // ImageAnalyzer
    private var imageAnalyzer: ImageAnalysis? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding WordScanner layout to the variable
        viewBinding = ActivityWordScannerBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Requesting camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        viewBinding.imageCaptureButton.setOnClickListener { takePhoto() }

        outputDirectory = getOutputDirectory()!!
    }

    private fun getOutputDirectory(): File? {
        var mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it,resources.getString(R.string.app_name)).apply {mkdir()}}

            return if(mediaDir != null && mediaDir.exists())
                mediaDir
        else
                filesDir
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        val file = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT,
                Locale.GERMANY
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(file)
            .build()



        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }
                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults){
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)

                }

            }
        )

    }
    private class myCallback: ImageCapture.OnImageCapturedCallback() {
        private lateinit var cameraExecutor: ExecutorService
        val imageAnalyzer = ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, MyImageAnalyzer())
            }

        override fun onCaptureSuccess(image: ImageProxy) {
            Log.i("OLLIE", "CAPTURED SUCCES")
            imageAnalyzer
        }
    }


    private fun startCamera() {
        // A singleton which can be used to bind the lifecycle of cameras to any LifecycleOwner within an application's process.
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview with a Surface as implementation
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private class MyImageAnalyzer : ImageAnalysis.Analyzer {
        @ExperimentalGetImage
        override fun analyze(image: ImageProxy) {
            val mediaImage = image.image
            if (mediaImage != null) {
                val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                val image = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
                val result = recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        val resultText = visionText.text
                        Log.i("OLLIE", "ABOUT TO LOG RESULT")
                        Log.i("OLLIE", resultText)

                    }
                    .addOnFailureListener { e ->
                        // Task failed with an exception
                        // ...
                    }
                // Pass image to an ML Kit Vision API
                // ...
            }
        }
    }
}
