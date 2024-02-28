package com.example.homework_3

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

        val animalList = listOf("Dog", "Cat", "Bear", "Rabbit")
        val animalAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, animalList)
        val animalListView = findViewById<ListView>(R.id.animalList)
        animalListView.adapter = animalAdapter

        animalListView.setOnItemClickListener { list, item, position, id ->
            val selectedItem = list.getItemAtPosition(position).toString()
            Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show()

            val imageIdOfSelectedAnimal = when (position) {
                0 -> R.drawable.dog
                1 -> R.drawable.cat
                2 -> R.drawable.bear
                else -> R.drawable.rabbit
            }
        }
    }
}