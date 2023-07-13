package com.sevban.contextandlifecycle

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.sevban.contextandlifecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "First Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.i(TAG, "onCreate")

    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
        //sadeleştir- onStarta taşı
        binding.navigateToComposeScreen.setOnClickListener {
            //What does "if you pass application context to an intent which
            //is used to start an activity that means you lose your theme min. 12:13" mean??
            startActivity(Intent(this, SecondActivity::class.java))
        }

        binding.showPermissionDialog.setOnClickListener {
            //Get activity to onPause() state with a permission dialog.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                100
            )
        }

        binding.showNormalDialog.setOnClickListener {
            //Activity context because its tied to UI but application context is not.
            //Note: Normal dialogs don't get our activity to pause state BUT Permission Dialogs does.
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
        //Called when user intentionally leaved the activity.
        Log.i(TAG, "onLeaveHint")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSavedInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i(TAG, "onRestoreInstanceState")
        //savedInstanceState.putString("stateToBeRestored", "myState")
        super.onRestoreInstanceState(savedInstanceState)
    }
}