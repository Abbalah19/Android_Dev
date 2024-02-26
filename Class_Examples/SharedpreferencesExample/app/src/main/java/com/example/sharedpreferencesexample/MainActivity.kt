package com.example.sharedpreferencesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    val FILE_NAME = "user_data"

    private lateinit var nameText: EditText
    private lateinit var emailText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the name and email EditText fields from the corresponding views in the layout
        nameText = findViewById(R.id.name_text)
        emailText = findViewById(R.id.email_text)
    }



    fun saveData(view: View) {

        // Create a SharedPreferences instance using the specified file name and mode
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        // Create an editor to modify SharedPreferences data
        val editor = sharedPreferences.edit()

        // Store data with (key, value) pairs in SharedPreferences
        // store name coming from the editText with the key "name" in SharedPreferences
        editor.putString("name", nameText.text.toString())
        // store email coming from the editText with the key "email" in SharedPreferences
        editor.putString("email", emailText.text.toString())

        // apply changes to SharedPreferences -- DO NOT FORGET!!!
        editor.apply()

        // Clear the editTexts as well
        nameText.text.clear()
        emailText.text.clear()

        Toast.makeText(this, "The data has been saved!", Toast.LENGTH_SHORT).show()
    }


    fun loadData(view: View) {

        // Create a SharedPreferences instance using the specified file name and mode to retrieve data
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        // Get name
        val name = sharedPreferences.getString("name", "")
        // Get email
        val email = sharedPreferences.getString("email", "")

        // Set the obtained values to editTexts
        nameText.setText(name)
        emailText.setText(email)
    }


    fun clearAllData(view: View) {

        // This method is pretty much the same as saveData method but
        // it clears/forgets the previously saved data (if any)

        // Create a SharedPreferences instance using the specified file name and mode
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Clear the saved data (if previously saved)
        editor.clear()
        // apply changes -- DO NOT FORGET!!!
        editor.apply()

        // Clear the editTexts as well
        nameText.text.clear()
        emailText.text.clear()

        Toast.makeText(this, "All data has been cleared!", Toast.LENGTH_SHORT).show()
    }
}