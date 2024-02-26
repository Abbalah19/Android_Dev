package com.example.intentwithreturningdata

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // create a new ActivityResultLauncher to launch the second activity and handle the result
        // When the result is returned, the result parameter will contain the data and resultCode (e.g., OK, Cancelled etc.).
        val secondActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                // extract the data
                val data = result.data
                val status = data?.getStringExtra("status")
                val phone = data?.getIntExtra("phone", 0)

                // For testing purpose, print the values in the logcat
                Log.d(TAG, "status: $status")
                Log.d(TAG, "phone: $phone")

                // Display the data coming from the second activity
                val statusTextView = findViewById<TextView>(R.id.tv_status)
                statusTextView.text = status.toString()
                // Initially the visibility of this textview was set to invisible, just make it visible
                statusTextView.visibility = View.VISIBLE

                val phoneTextView = findViewById<TextView>(R.id.tv_phone)
                phoneTextView.text = phone.toString()
                // Initially the visibility of this textview was set to invisible, just make it visible
                phoneTextView.visibility = View.VISIBLE
            }
        }

        // the second activity will only be launched when the button is clicked.
        findViewById<Button>(R.id.button_to_launch_second_activity).setOnClickListener {

            // prepare the data to be sent to the second activity
            val firstName = findViewById<EditText>(R.id.firstname_input).text.toString()
            val lastName = findViewById<EditText>(R.id.lastname_input).text.toString()
            val tvAge = findViewById<EditText>(R.id.age_input)
            val age = tvAge.text.toString().toIntOrNull()

            // Hide the keyboard after entering age
            tvAge.hideKeyboard()

            // Check to make sure the fields are not empty
            if (firstName.isEmpty() || lastName.isEmpty() || age == null) {
                Toast.makeText(this, "Please enter all of the requested information", Toast.LENGTH_SHORT).show()
            } else {
                // For testing purpose, print the values in the logcat
                Log.d(TAG, "firstName: $firstName")
                Log.d(TAG, "lastName: $lastName")
                Log.d(TAG, "age: $age")

                //Create an Intent object with two parameters: 1) context, 2) class of the activity to launch
                val myIntent = Intent(this, SecondActivity::class.java)

                // put "extras" into the intent for access in the second activity
                myIntent.putExtra("firstName", firstName)
                myIntent.putExtra("lastName", lastName)
                myIntent.putExtra("age", age)

                // Start the new Activity with the given intent and registers the ActivityResultCallback
                secondActivityLauncher.launch(myIntent)
            }
        }

    }


    // Helper function to hide the keyboard for any view/widget
    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}