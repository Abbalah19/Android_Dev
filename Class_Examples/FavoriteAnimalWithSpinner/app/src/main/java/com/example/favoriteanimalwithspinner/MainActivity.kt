package com.example.favoriteanimalwithspinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create a list of some strings that will be shown in the spinner
        val animalList = listOf("Dog", "Cat", "Bear", "Rabbit")

        // Create an adapter with 3 parameters: activity (this), layout (using a pre-built layout), list to show
        val animalAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, animalList)

        // Store the the listView widget in a variable
        val animalSpinner = findViewById<Spinner>(R.id.animal_spinner)

        // set the adapter to the spinner
        animalSpinner.adapter = animalAdapter

        // set the onItemSelectedListener as (this).  (this) refers to this activity that implements OnItemSelectedListener interface
        animalSpinner.onItemSelectedListener = this
    }

    // The following two methods are callback methods of OnItemSelectedListener interface
    override fun onNothingSelected(parent: AdapterView<*>?) {
        //Callback method to be invoked when the selection disappears from this view. For example,
        // if the list becomes empty and the ArrayAdapter is notified, this callback will be invoked
        Toast.makeText(this, "Nothing is selected!", Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        // you can determine which item in the dropdown list is selected
        val selectedItem = parent?.getItemAtPosition(position).toString()
        Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()

        // Based on the index of position selected, set the corresponding image
        // For instance, if position is equal to 0, assign the value of R.drawable.dog to imageIdOfSelectedAnimal
        val imageIdOfSelectedAnimal = when(position){
            0 -> R.drawable.dog
            1 -> R.drawable.cat
            2 -> R.drawable.bear
            else -> R.drawable.rabbit
        }

        // Set the image resource based on selected imageId
        findViewById<ImageView>(R.id.animal_image).setImageResource(imageIdOfSelectedAnimal)
    }
}