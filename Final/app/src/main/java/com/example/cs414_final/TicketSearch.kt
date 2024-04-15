package com.example.cs414_final

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cs414_final.Event
import com.example.cs414_final.EventsList
import com.example.cs414_final.RecyclerViewAdapter
import com.example.cs414_final.TicketMasterService
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "TicketSearch"

class TicketSearch : AppCompatActivity() {
    private val BASE_URL = "https://app.ticketmaster.com/discovery/v2/"
    private val API_KEY = "Qvr3MbRtSApocNPL6TbfDGdHnlm087nD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_search)

        val eventsList = ArrayList<Event>()
        val adapter = RecyclerViewAdapter(eventsList)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val searchButton = findViewById<Button>(R.id.search_button)
        val TicketMasterService = retrofit.create(TicketMasterService::class.java)

        searchButton.setOnClickListener {
            val keyword = findViewById<TextView>(R.id.keyword_editTextText).text.toString()
            val city = findViewById<TextView>(R.id.location_editTextText).text.toString()
            hideKeyboard()

            // Check if keyword and city are empty, make call to showAlert()
            if (keyword.isEmpty() && city.isEmpty()) {
                showAlert("Both Keyword and City are empty", "Please enter a keyword and city to search for events")
            }
            else if (keyword.isEmpty()) {
                showAlert("Keyword is empty", "Please enter a keyword to search for events")
            }
            else if (city.isEmpty()) {
                showAlert("City is empty", "Please enter a city to search for events")
            }
            else

            // if both terms have been entered, make a call to TicketMasterService.getEvents()
                TicketMasterService.getEvents(API_KEY, keyword, city, "date,asc")
                    .enqueue(object : Callback<EventsList> {
                        override fun onResponse(
                            call: Call<EventsList>,
                            response: Response<EventsList>
                        ) {
                            Log.d(TAG, "onResponse: $response")

                            val body = response.body()
                            if (body == null || body._embedded == null) {
                                // if response is null, hide the recycler and make the noEntryText visible
                                findViewById<RecyclerView>(R.id.recyclerView).visibility = RecyclerView.INVISIBLE
                                findViewById<TextView>(R.id.noEntryText).visibility = TextView.VISIBLE
                                Log.w(TAG, "Valid response was not received or events list is null")
                                return
                            }
                            // clear old list
                            eventsList.clear()
                            // reset the recycler view and noEntryText visibility
                            findViewById<TextView>(R.id.noEntryText).visibility = TextView.INVISIBLE
                            findViewById<RecyclerView>(R.id.recyclerView).visibility = RecyclerView.VISIBLE
                            // add new events to the list
                            eventsList.addAll(body._embedded.events)
                            // update the recycler view
                            adapter.notifyDataSetChanged()
                            // Scroll to the top of the RecyclerView
                            recyclerView.layoutManager?.scrollToPosition(0)
                        }

                        override fun onFailure(call: Call<EventsList>, t: Throwable) {
                            Log.e(TAG, "onFailure: $t")
                        }
                    })
        }
    }

    fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
    fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Accept") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    fun signOut (view: View) {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
}