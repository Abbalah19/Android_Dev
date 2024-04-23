package com.example.cs414_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SavedEvents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_events)
    }

    fun backToSearch(view: View) {
        val intent = Intent(this, TicketSearch::class.java)
        finish()
        startActivity(intent)
    }
}