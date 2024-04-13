package com.example.cs414_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun signInUser(view: View) {
        Toast.makeText(this, "Sign in", Toast.LENGTH_SHORT).show()
        // val intent = Intent(this, SignInActivity::class.java)
        //startActivity(intent)
    }
    fun createNewAccount(view: View) {
        //Toast.makeText(this, "New Account", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, CreateNewAccount::class.java)
        startActivity(intent)
    }
}