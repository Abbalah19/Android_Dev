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
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView



class MainActivity : AppCompatActivity() {

    /*
    // set up all the sharedPreferences stuff.
    private lateinit var dogRating: EditText
    private lateinit var catRating: EditText
    private lateinit var bearRating: EditText
    private lateinit var rabbitRating: EditText
    private lateinit var historyFlag: EditText
    private lateinit var recentAnimal: EditText
    private val TAG = "MainActivity"
    import android.widget.EditText
    import androidx.activity.result.contract.ActivityResultContracts
     */

    val FILE_NAME = "rating_data"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    val animalList = mutableListOf("Dog", "Cat", "Bear", "Rabbit")
    lateinit var animalAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // app used before? cleared on delete
        checkHistory()

        animalAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, animalList)
        val animalListView = findViewById<ListView>(R.id.animalList)
        animalListView.adapter = animalAdapter

        animalListView.setOnItemClickListener { list, item, position, id ->
            var selectedItem = list.getItemAtPosition(position).toString()
            //Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()

            // trim string before sending data, stops rating info so Dog stays as Dog not Dog: 4/5
            if (selectedItem.contains(":")){
                selectedItem = selectedItem.substringBefore(":").trim()
            }
            // send click to second activity
            val myIntent = Intent(this, SecondActivity::class.java)
            myIntent.putExtra("selectedItem", selectedItem)
            startActivity(myIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        updateList()
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

        // -1f is a default value for pref file. will return if not changed or value not found
        editor.putFloat("dogRating", -1f)
        editor.putFloat("catRating", -1f)
        editor.putFloat("bearRating", -1f)
        editor.putFloat("rabbitRating", -1f)
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
        val defaultFloat = -1f
        sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val returnedAnimal = sharedPreferences.getString("recentAnimal", defaultString)
        val rating = sharedPreferences.getFloat(returnedAnimal, defaultFloat)

        // if change made to animal, update the bottom of the page
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

    private fun updateList() {
        sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        for (animal in animalList) {
            // rating gets updated to XXX: rating 2/5. must trim or won't update right
            val animalName = animal.split(":").first().trim()
            val rating = sharedPreferences.getFloat(animalName, -1f)
            //Toast.makeText(this, "$animalName", Toast.LENGTH_SHORT).show()
            if (rating != -1f) {
                val index = animalList.indexOf(animal)
                animalList[index] = "$animalName: rating $rating/5"
            }
        }
        animalAdapter.notifyDataSetChanged()
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