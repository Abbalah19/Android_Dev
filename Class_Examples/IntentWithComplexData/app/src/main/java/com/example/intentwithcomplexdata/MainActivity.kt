package com.example.intentwithcomplexdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
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
        if (firstName.isEmpty() || lastName.isEmpty() || age == null) {
            Toast.makeText(
                this,
                "Please enter all of the requested information",
                Toast.LENGTH_SHORT
            ).show()
            return
        }


        // create instances of the User class with the given values
        val user = User(firstName, lastName, age)


        //Create an Intent object with two parameters: 1) context, 2) class of the activity to launch
        val myIntent = Intent(this, SecondActivity::class.java)

        // put "extras" into the intent for access in the second activity
        myIntent.putExtra("user", user)

        // Start the new Activity with startActivity()
        startActivity(myIntent)


    }
}