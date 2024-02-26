package com.example.favoriteanimalwithlistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create a list of some strings that will be shown in the listview
        val animalList = listOf("Dog", "Cat", "Bear", "Rabbit")

        // Create an adapter with 3 parameters: activity (this), layout (using a pre-built layout), list to show
        val animalAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, animalList)

        // Store the the listView widget in a variable
        val animalListView = findViewById<ListView>(R.id.animal_list)

        // set the adapter to the listView
        animalListView.adapter = animalAdapter


        // Registering setOnItemClickListener that listens item click events in the listview
        animalListView.setOnItemClickListener { list, item, position, id ->

            // Determine which item in the list is selected
            val selectedItem = list.getItemAtPosition(position).toString()
            Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()


            // Based on the index of position selected, set the corresponding image
            // For instance, if position is equal to 0, assign the value of R.drawable.dog to imageIdOfSelectedAnimal
            val imageIdOfSelectedAnimal = when(position) {
                0 -> R.drawable.dog
                1 -> R.drawable.cat
                2 -> R.drawable.bear
                else -> R.drawable.rabbit
            }

            // Set the imageId based on the selected index
            findViewById<ImageView>(R.id.image_animal).setImageResource(imageIdOfSelectedAnimal)

        }
    }
}