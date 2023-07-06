package com.sevban.contextandlifecycle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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