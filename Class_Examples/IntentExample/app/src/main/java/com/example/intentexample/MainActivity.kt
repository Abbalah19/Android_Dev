package com.example.intentexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // A tag used for log messages -- you can use different name if you like
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openSecondActivity(view: View){

        // Prepare the data to be sent to the second activity
        val firstName = findViewById<EditText>(R.id.firstname_input).text.toString()
        val lastName = findViewById<EditText>(R.id.lastname_input).text.toString()
        val age = findViewById<EditText>(R.id.age_input).text.toString().toIntOrNull()

        // Check to make sure the fields are not empty
        if (firstName.isEmpty() or lastName.isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            return
        }
        if (age == null){
            Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show()
            return
        }

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

        // Start the new Activity with startActivity()
        startActivity(myIntent)

    }
}