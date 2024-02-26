package com.example.intentwithcomplexdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    private val TAG = "SecondActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Access any extras passed in intent using methods like getIntExtra(), getStringExtra
        //val user : User = intent.getSerializableExtra("user") as User

        //val firstName = user.firstName
        //val lastName = user.lastName
        //val age = user.age

        // Or more concisely they can be accessed as follows
        val (firstName, lastName, age) = intent.getSerializableExtra("user") as User

        // For testing purpose, print the values in the logcat
        Log.d(TAG, "firstName: $firstName ")
        Log.d(TAG, "lastName: $lastName")
        Log.d(TAG, "age: $age")

        // Display the incoming data
        findViewById<TextView>(R.id.tv_first_name).text = firstName
        findViewById<TextView>(R.id.tv_last_name).text = lastName
        findViewById<TextView>(R.id.tv_age).text = age.toString()
    }
}