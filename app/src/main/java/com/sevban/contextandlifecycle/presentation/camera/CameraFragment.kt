package com.sevban.contextandlifecycle.presentation.camera

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings.ZoomDensity
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sevban.contextandlifecycle.databinding.FragmentCameraBinding
import com.sevban.contextandlifecycle.presentation.camera.components.ZoomableImage
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class CameraFragment : Fragment() {
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraXManager: CameraXManager
    private var imageUriFromLambda: Uri? = null
    private var shouldShowImage by mutableStateOf(false)

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && it.value == false)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(
                    requireContext(),
                    "Permission request denied",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                cameraXManager.startCamera(binding.viewFinder.surfaceProvider, this)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cameraXManager = CameraXManagerImpl(requireActivity(), lifecycle)
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (allPermissionsGranted())
            cameraXManager.startCamera(binding.viewFinder.surfaceProvider, this)
        else
            requestPermissions()
        setViews()
    }

    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
        //here, they actually used baseContext why ?
    }

    private fun setViews() {
        binding.imageCaptureButton.setOnClickListener {

            cameraXManager.takePicture(
                onErrorImageCapturing = { throwable ->
                    Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
                },
                onImageSuccesfullyCaptured = { imageUri ->
                    imageUriFromLambda = imageUri
                    shouldShowImage = true
                    requireActivity().runOnUiThread {
                        binding.composeImage.visibility = View.VISIBLE
                        binding.viewFinder.visibility = View.GONE
                        binding.imageCaptureButton.visibility = View.GONE
                    }
                }
            )

        }

        binding.composeImage.setContent {
            LaunchedEffect(key1 = Unit, shouldShowImage) {
                delay(1033.seconds)
                shouldShowImage = false
                requireActivity().runOnUiThread {
                    binding.composeImage.visibility = View.GONE
                    binding.viewFinder.visibility = View.VISIBLE
                    binding.imageCaptureButton.visibility = View.VISIBLE
                }
            }

            if (shouldShowImage)
                ZoomableImage(uri = imageUriFromLambda)
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}
