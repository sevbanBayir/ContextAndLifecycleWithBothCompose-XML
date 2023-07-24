package com.sevban.contextandlifecycle.presentation.first_activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.sevban.contextandlifecycle.databinding.ActivityMainBinding
import com.sevban.contextandlifecycle.presentation.second_activity.SecondActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "First Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.i(TAG, "onCreate")
        setViews()
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        Log.i(TAG, "onLeaveHint")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSavedInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i(TAG, "onRestoreInstanceState")
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun setViews() {

        binding.basketView.tvQuantity.text = "14"
        binding.navigateToComposeScreen.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        binding.showPermissionDialog.setOnClickListener {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                100
            )
        }

        binding.showNormalDialog.setOnClickListener {
            AlertDialog.Builder(this)
                .apply {
                    setMessage("Sample activity dialog message")
                    setPositiveButton(
                        "OK"
                    ) { dialog, id ->
                        startActivity(Intent(applicationContext, SecondActivity::class.java))
                    }
                }.create().show()
        }
    }
}