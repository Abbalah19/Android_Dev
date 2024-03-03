package com.example.homework_3

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    val FILE_NAME = "rating_data"

    private lateinit var dogRating: EditText
    private lateinit var catRating: EditText
    private lateinit var bearRating: EditText
    private lateinit var rabbitRating: EditText
    private lateinit var historyFlag: EditText
    private lateinit var recentAnimal: EditText

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    val animalList = mutableListOf("Dog", "Cat", "Bear", "Rabbit")
    lateinit var animalAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkHistory()

        animalAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, animalList)
        val animalListView = findViewById<ListView>(R.id.animalList)
        animalListView.adapter = animalAdapter

        animalListView.setOnItemClickListener { list, item, position, id ->
            var selectedItem = list.getItemAtPosition(position).toString()
            //Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()

            // trim string before sending data
            if (selectedItem.contains(":")){
                selectedItem = selectedItem.substringBefore(":").trim()
            }
            val myIntent = Intent(this, SecondActivity::class.java)
            myIntent.putExtra("selectedItem", selectedItem)
            startActivity(myIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        checkHistory()
    }

    private fun checkHistory(){
        sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        editor = sharedPreferences.edit()
        // use a flag to check for any pre-existing data, if no then initialize some with default
        // data, if yes then fill screen with saved data.
        if (sharedPreferences.contains("historyFlag")){
            val historyFlagCheck = sharedPreferences.getInt("historyFlag", -1)
            when (historyFlagCheck){
                -1 -> {
                    //Toast.makeText(this, "-1", Toast.LENGTH_SHORT).show()
                    firstTimeSetup()
                }
                0 -> {
                    //Toast.makeText(this, "0", Toast.LENGTH_SHORT).show()
                    firstTimeSetup()
                }
                1 -> {
                    //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()
                    getData()
                }
            }
        }
        else firstTimeSetup()
    }
    private fun firstTimeSetup(){
        findViewById<TextView>(R.id.mostRecent_textview).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.recentAnimal_textview).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.rating_textview).visibility = View.INVISIBLE
        findViewById<ImageView>(R.id.imageView).visibility = View.INVISIBLE
        findViewById<RatingBar>(R.id.ratingBar).visibility = View.INVISIBLE

        editor.putString("dogRating", "0")
        editor.putString("catRating", "0")
        editor.putString("bearRating", "0")
        editor.putString("rabbitRating", "0")
        editor.putInt("historyFlag", 0)
        editor.apply()
    }

    fun getData(){
        // when history flag is set, start by making everything visible.
        findViewById<TextView>(R.id.mostRecent_textview).visibility = View.VISIBLE
        findViewById<TextView>(R.id.recentAnimal_textview).visibility = View.VISIBLE
        findViewById<TextView>(R.id.rating_textview).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.imageView).visibility = View.VISIBLE
        findViewById<RatingBar>(R.id.ratingBar).visibility = View.VISIBLE

        val defaultString = "null"
        val defaultFloat = 0.0f
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)

        val returnedAnimal = sharedPreferences.getString("recentAnimal", defaultString)
        val rating = sharedPreferences.getFloat(returnedAnimal, defaultFloat)
        //Toast.makeText(this, "$returnedAnimal", Toast.LENGTH_SHORT).show()
        if (returnedAnimal != defaultString) {
            val mostRecentTextView = findViewById<TextView>(R.id.recentAnimal_textview)
            mostRecentTextView.text = "Animal: $returnedAnimal"
            val mostRecentRating = findViewById<RatingBar>(R.id.ratingBar)
            mostRecentRating.setRating(rating)

            val imageView = findViewById<ImageView>(R.id.imageView)
            when (returnedAnimal) {
                "Dog" -> imageView.setImageResource(R.drawable.dog)
                "Cat" -> imageView.setImageResource(R.drawable.cat)
                "Bear" -> imageView.setImageResource(R.drawable.bear)
                "Rabbit" -> imageView.setImageResource(R.drawable.rabbit)
            }
        }
    }

    fun deleteData(view: View){
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.clear()
        editor.apply()

        animalList.clear()
        animalList.addAll(listOf("Dog", "Cat", "Bear", "Rabbit"))
        animalAdapter.notifyDataSetChanged()
        firstTimeSetup()

        Toast.makeText(this, "All data has been cleared!", Toast.LENGTH_SHORT).show()
    }
}