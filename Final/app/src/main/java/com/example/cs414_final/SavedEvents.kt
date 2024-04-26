package com.example.cs414_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

private const val TAG = "SavedEvents"

class SavedEvents : AppCompatActivity() {

    private val db = Firebase.firestore // initialize the db
    private val currentUser = FirebaseAuth.getInstance().currentUser // get the current user
    private lateinit var adapter: SavedEventRecyclerViewAdapter // initialize the adapter
    private lateinit var recyclerView: RecyclerView // initialize the recycler view

    // use db to create a list of savedEvent objects, the objects are used to populate the recycler view
    private val savedList = ArrayList<SavedEventData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_events)

        recyclerView = findViewById<RecyclerView>(R.id.saved_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter and set it to the RecyclerView
        adapter = SavedEventRecyclerViewAdapter(savedList)
        recyclerView.adapter = adapter

        getSavedEvents()
    }

    private fun getSavedEvents() {
        Log.d(TAG, "getSavedEvents called")
        db.collection("userProfiles") // get the collection of userProfiles
            .document(currentUser?.uid.toString()) // get the current user's document
            .collection("savedEvents") // get the collection of savedEvents
            .get()
            .addOnSuccessListener { result ->
                // success? add the data to the savedList
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val event = document.toObject(SavedEventData::class.java)
                    savedList.add(event)
                }
                // update the recycler view
                adapter.notifyDataSetChanged()
                // Scroll to the top of the RecyclerView
                recyclerView.layoutManager?.scrollToPosition(0)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}