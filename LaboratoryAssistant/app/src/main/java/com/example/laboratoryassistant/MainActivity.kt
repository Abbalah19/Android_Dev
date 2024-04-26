package com.example.laboratoryassistant

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = arrayOf("Dilution Guide", "Standard Maker", "True Value Calculator")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        listView.setOnItemLongClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    val intent = Intent(this, DilutionGuideActivity::class.java)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(this, StandardMakerActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(this, TrueValueCalculator::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }
}