package com.example.implicitintentexamples

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun callButton(view: View) {
        // Dial a phone number
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:1234567890")
        startActivity(callIntent)
    }

    fun loadURLButton(view: View) {
        // Load a web URL
        val browserIntent = Intent(Intent.ACTION_VIEW)
        // Search for a key word car -- >
        browserIntent.data = Uri.parse("https://www.google.com/search?q=car")
        startActivity(browserIntent)
    }

    fun openMapButton(view: View) {
        // Open a MAP that points at a given latitude and longitude (z=zoom level)
        val location = Uri.parse("geo:41.692438,-72.7680165?z=14")
        val mapIntent = Intent(Intent.ACTION_VIEW, location)
        startActivity(mapIntent)
    }
}