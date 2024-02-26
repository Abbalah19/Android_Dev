package com.example.lifecyclesdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var textTyped: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // The activity is being created.
        Log.i(TAG, "onCreate was called")

        // Initialize textView
        textTyped = findViewById(R.id.text_typed)
    }

    override fun onStart() {
        super.onStart()
        // The activity has started
        Log.i(TAG, "onStart was called")

    }

    override fun onResume() {
        super.onResume()
        // The activity has become visible, it is now "resumed"
        Log.i(TAG, "onResume was called")
    }

    override fun onPause() {
        super.onPause()
        // Another activity is taking focus
        // this activity is about to be "paused"
        Log.i(TAG, "onPause was called")
    }

    override fun onStop() {
        super.onStop()
        // The activity is no longer visible
        // it is now "stopped"
        Log.i(TAG, "onStop was called")
    }

    override fun onDestroy() {
        super.onDestroy()
        // The activity is about to be destroyed
        Log.i(TAG, "onDestroy was called")
    }

    override fun onRestart() {
        super.onRestart()
        // The activity is between stopped and started
        Log.i(TAG, "onRestart was called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the state of program such as text in the textview when the states changes
        // such as due to flipping from portrait to landscape
        Log.i(TAG, "onSaveInstanceState was called")
        outState.putString("textTyped", textTyped.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Called when there is a saved instance that is previously
        // saved by using onSaveInstanceState()
        // Set the value back to TextView, which could be done in onCreate as well
        Log.i(TAG, "onRestoreInstanceState was called")
        val previousText = savedInstanceState.getString("textTyped", "")
        textTyped.text = previousText
    }

    fun launchSecondActivity(view: View) {
        // Launch the second activity
        val intent = Intent(this, SecondActivity::class.java)
        Log.i(TAG, "Launching the second activity")
        startActivity(intent)
    }

    fun enterTextButton(view: View) {
        val editTextContent = findViewById<EditText>(R.id.editText).text.toString()
        textTyped.text = "You typed: $editTextContent"
    }

}