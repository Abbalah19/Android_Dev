package com.example.homework_3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast

class SecondActivity : AppCompatActivity() {

    private val TAG = "SecondActivity"
    lateinit var selectedItem: String
    val FILE_NAME = "rating_data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        selectedItem = intent.getStringExtra("selectedItem") ?:""

        // Set Title and image
        findViewById<TextView>(R.id.act2_animal_title_textview).text = selectedItem
        val imageView = findViewById<ImageView>(R.id.imageView2)
        val ratingBar = findViewById<RatingBar>(R.id.act2_rating_bar)
        when (selectedItem) {
            "Dog" -> imageView.setImageResource(R.drawable.dog)
            "Cat" -> imageView.setImageResource(R.drawable.cat)
            "Bear" -> imageView.setImageResource(R.drawable.bear)
            "Rabbit" -> imageView.setImageResource(R.drawable.rabbit)
        }
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val rating = sharedPreferences.getFloat(selectedItem, -1f)
        ratingBar.setRating(rating)
    }

    fun saveData(view: View){
        val ratingBar = findViewById<RatingBar>(R.id.act2_rating_bar)
        val rating = ratingBar.rating
        //Toast.makeText(this, "$selectedItem", Toast.LENGTH_SHORT).show()
        //Toast.makeText(this, "$rating", Toast.LENGTH_SHORT).show()

        val sharedPreferences = getSharedPreferences(MainActivity().FILE_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("$selectedItem", rating)
        editor.putString("recentAnimal", selectedItem)
        editor.putInt("historyFlag", 1)
        editor.apply()
        finish()
    }
}