package com.sevban.contextandlifecycle

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.sevban.contextandlifecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.i(TAG, "onCreate in MainActivity")

        //Activity context because its tied to UI but application context is not.
        //Note: Normal dialogs don't get our activity to pause state BUT Permission Dialogs does.
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Sample activity dialog message")
            setPositiveButton(
                "OK"
            ) { dialog, id -> }
        }
        builder.create().show()

        //Get activity to onPause() state.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            100
        )

        // Accessing shared preferences using the application context
        val sharedPreferences = this.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("name", "sevban").apply()

        val name = sharedPreferences.getString("name", "")
        Toast.makeText(this, "Name: $name", Toast.LENGTH_LONG).show()

        //Application context can be used in a situation which a singleton is used that needs a context
        //we MUST pass app context to that bcs

    }

    override fun onStart() {
        super.onStart()
        //Thread.sleep(5000)
        Log.i(TAG, "onStart in MainActivity")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume in MainActivity")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause in MainActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop in MainActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy in MainActivity")
    }
}