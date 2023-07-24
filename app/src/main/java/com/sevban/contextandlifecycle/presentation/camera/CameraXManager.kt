package com.sevban.contextandlifecycle.presentation.camera

import android.net.Uri
import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner

interface CameraXManager {
    fun startCamera(surfaceProvider: Preview.SurfaceProvider, owner: LifecycleOwner)
    fun takePicture(
        onErrorImageCapturing: (Throwable) -> Unit,
        onImageSuccesfullyCaptured: (Uri) -> Unit
    )
}