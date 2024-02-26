package com.example.intentwithreturningdata

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    private val TAG = "SecondActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // The following code accesses any extras passed in intent using methods like getIntExtra(), getStringExtra
        // Remember: intent is like a global variable that can be accessed in an Activity
        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        val age = intent.getIntExtra("age", 0)

        // For testing purpose, print the values in the logcat
        Log.d(TAG, "firstName: $firstName ")
        Log.d(TAG, "lastName: $lastName")
        Log.d(TAG, "age: $age")

        // Display the received data
        findViewById<TextView>(R.id.tv_first_name).text = firstName
        findViewById<TextView>(R.id.tv_last_name).text = lastName
        findViewById<TextView>(R.id.tv_age).text = age.toString()
    }

    fun returnDataToFirstActivity(view: View) {

        // Need to create an intent to go back
        val myIntent = Intent()

        // Get the information from the edittexts
        val status = findViewById<EditText>(R.id.status_input).text.toString()
        val phone = findViewById<EditText>(R.id.phone_input).text.toString().toIntOrNull()

        // Store any extra data in the intent
        myIntent.putExtra("status", status)
        myIntent.putExtra("phone", phone)

        // Set the activity's result to RESULT_OK
        setResult(Activity.RESULT_OK, myIntent)
        // Stop and close the second activity
        finish()

    }
}