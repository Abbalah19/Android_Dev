package com.example.lifecyclesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SecondActivity : AppCompatActivity() {

    private val TAG = "SecondActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        // The activity is being created.
        Log.d(TAG, "onCreate was called")
    }

    override fun onStart() {
        super.onStart()
        // The activity is about to become visible.
        Log.d(TAG, "OnStart was called")
    }

    override fun onResume() {
        super.onResume()
        // The activity has become visible, it is now "resumed"
        Log.d(TAG, "onResume was called")
    }

    override fun onPause() {
        super.onPause()
        // Another activity is taking focus
        // this activity is about to be "paused"
        Log.d(TAG, "onPause was called")
    }

    override fun onStop() {
        super.onStop()
        // The activity is no longer visible
        // it is now "stopped"
        Log.d(TAG, "onStop was called")
    }

    override fun onDestroy() {
        super.onDestroy()
        // The activity is about to be destroyed
        Log.d(TAG, "onDestroy was called")
    }


    override fun onRestart() {
        super.onRestart()
        // The activity is between stopped and started
        Log.d(TAG, "onRestart was called")
    }

}
