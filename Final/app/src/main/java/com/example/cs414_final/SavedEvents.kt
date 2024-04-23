package com.example.cs414_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

private const val TAG = "SavedEvents"

class SavedEvents : AppCompatActivity() {

    private val db = Firebase.firestore
    private val currentUser = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_events)
        getSavedEvents()
    }

    private fun getSavedEvents(){
        Log.d(TAG, "getSavedEvents called")
        // collection from userProfiles -> document match current user -> collection savedEvents -> now what?
        db.collection("userProfiles")
            .document(currentUser?.uid.toString())
            .collection("savedEvents")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun backToSearch(view: View) {
        val intent = Intent(this, TicketSearch::class.java)
        finish()
        startActivity(intent)
    }
}