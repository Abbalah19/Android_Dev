package com.example.favoriteanimalwithlistviewremovable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // Note that it is not really necessary to make these variables global in this example
    // but it may help you in a different context where you actually want to make a global variable

    // Create a list of some strings that will be shown in the listview
    private val animalList = ArrayList<String>()

    //lateinit allows to initialize variable before the code runs so no need to initialize the variable to null here
    lateinit var animalAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Put some items to the list
        animalList.add("dog")
        animalList.add("cat")
        animalList.add("bear")
        animalList.add("rabbit")


        // Create an adapter with 3 parameters: activity (this), layout (using a pre-built layout), list to show
        animalAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, animalList)

        // Store the the listView widget in a variable
        val animalListView = findViewById<ListView>(R.id.animal_list)

        // set the adapter to the listView
        animalListView.adapter = animalAdapter

        // Registering setOnItemClickListener that listens item click events in the listview
        animalListView.setOnItemClickListener { list, item, position, id ->

            // Determine which item in the list is selected
            val selectedItem = list.getItemAtPosition(position).toString()
            Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()


            // Based on the selected item, set the corresponding animal image
            // For instance, if selectedItem is equal to dog, assign the value of R.drawable.dog to imageIdOfSelectedAnimal
            val imageIdOfSelectedAnimal = when(selectedItem) {
                "dog" -> R.drawable.dog
                "cat" -> R.drawable.cat
                "bear" -> R.drawable.bear
                else -> R.drawable.rabbit
            }

            // Set the imageId based on the selected index in the listview
            findViewById<ImageView>(R.id.animal_image).setImageResource(imageIdOfSelectedAnimal)

        }


        // Registering setOnItemLongClickListener callback method to be invoked when an item in this list has been clicked and held.
        // This callback methods returns boolean, true if the callback consumed the long click, false otherwise
        animalListView.setOnItemLongClickListener { parent, view, position, id ->

            // You can get the text of the selected item as shown below:
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "This is a long press, Deleting $selectedItem", Toast.LENGTH_SHORT).show()

            // Remove the item from the list
            animalList.removeAt(position)

            // Notify the adopter to update itself after removing an item from the list
            animalAdapter.notifyDataSetChanged()

            return@setOnItemLongClickListener true

        }
    }
}