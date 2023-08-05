package com.sevban.contextandlifecycle.presentation.camera

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.Preview.SurfaceProvider
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraXManagerImpl(
    private val context: Context,
    private val lifecycle: Lifecycle,
) : CameraXManager, DefaultLifecycleObserver {
    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null

    init {
        bindToLifecycle()
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun startCamera(surfaceProvider: SurfaceProvider, owner: LifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context) //singleton

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    owner, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(context))
    }

    override fun takePicture(
        onErrorImageCapturing: (Throwable) -> Unit,
        onImageSuccesfullyCaptured: (Uri) -> Unit
    ) {
        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    onErrorImageCapturing(exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    output.savedUri?.let { onImageSuccesfullyCaptured(it) }
                }
            }
        )
    }

    private fun bindToLifecycle() {
        if ((context as AppCompatActivity).lifecycle.currentState == Lifecycle.State.CREATED) {
            lifecycle.addObserver(this)
        }
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            lifecycle.removeObserver(this)
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}

