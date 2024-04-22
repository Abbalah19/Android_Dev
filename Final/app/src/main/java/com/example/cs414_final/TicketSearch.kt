package com.example.cs414_final

import android.annotation.SuppressLint
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.Manifest
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.location.LocationServices

private const val TAG = "TicketSearch"

@Suppress("NullChecksToSafeCall", "SENSELESS_COMPARISON")
class TicketSearch : AppCompatActivity() {
    private val BASE_URL = "https://app.ticketmaster.com/discovery/v2/"
    private val API_KEY = "Qvr3MbRtSApocNPL6TbfDGdHnlm087nD"
    private val db = Firebase.firestore
    private val currentUser = FirebaseAuth.getInstance().currentUser

    // for location services
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_search)
        setUser() // changes the welcome text to the user's username

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

        // When the search button is clicked...
        searchButton.setOnClickListener {
            hideKeyboard()
            val (keyword, city) = getKeywordCity()
            if (checkFields(keyword, city)) {
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
                                findViewById<RecyclerView>(R.id.recyclerView).visibility =
                                    RecyclerView.INVISIBLE
                                findViewById<TextView>(R.id.noEntryText).visibility =
                                    TextView.VISIBLE
                                Log.w(TAG, "Valid response was not received or events list is null")
                                return
                            }
                            updateRecyclerView(eventsList, body, adapter, recyclerView)
                        }

                        override fun onFailure(call: Call<EventsList>, t: Throwable) {
                            Log.e(TAG, "onFailure: $t")
                        }
                    })
            }
        }


        val nearMeButton = findViewById<Button>(R.id.near_me_button)
        nearMeButton.setOnClickListener {
            hideKeyboard()
            val keyword = getKeywordCity().first

            if (checkFieldsNearMe(keyword)) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
                }
                else {
                    //showAlert("Location already Granted", "Location permission has been granted")
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

                    fusedLocationClient.lastLocation.addOnSuccessListener {location ->
                        if (location != null) {
                            val geoPoint = GeoFireUtils.getGeoHashForLocation(
                                GeoLocation(location.latitude, location.longitude)).take(5)
                            Log.d(TAG, "!!!!!!!!geoPoint: $geoPoint")
                            TicketMasterService.getNearMe(API_KEY, keyword, "50","miles",geoPoint, "date,asc")
                                .enqueue(object : Callback<EventsList> {
                                    override fun onResponse(
                                        call: Call<EventsList>,
                                        response: Response<EventsList>
                                    ) {
                                        Log.d(TAG, "onResponse: $response")
                                        val body = response.body()
                                        if (body == null || body._embedded == null) {
                                            // if response is null, hide the recycler and make the noEntryText visible
                                            findViewById<RecyclerView>(R.id.recyclerView).visibility =
                                                RecyclerView.INVISIBLE
                                            findViewById<TextView>(R.id.noEntryText).visibility =
                                                TextView.VISIBLE
                                            Log.w(TAG, "Valid response was not received or events list is null")
                                            return
                                        }
                                        updateRecyclerView(eventsList, body, adapter, recyclerView)
                                    }

                                    override fun onFailure(call: Call<EventsList>, t: Throwable) {
                                        Log.e(TAG, "onFailure: $t")
                                    }
                                })
                        }
                    }
                }
            }
        }
    }


    // onRequestPermissionsResult is mostly From Co-pilot - I got to the point where I was getting the wrong
    // message and used AI to figure out the asynchronous operation
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    showAlert("Location Permission Granted","Location permission has been granted, press the Near Me button again to search for events near you.")
                } else {
                    showAlert("Location Permission Denied","Please Grant Location Permission In App Settings To Use This Feature")
                }
                return
            }
        }
    }

    private fun getKeywordCity(): Pair<String, String> {
        val keyword = findViewById<EditText>(R.id.keyword_editTextText).text.toString()
        val city = findViewById<EditText>(R.id.location_editTextText).text.toString()
        return Pair(keyword, city)
    }

    // Function to update the recycler view with the new events, notify the adapter of the change
    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecyclerView(eventsList: ArrayList<Event>, body: EventsList,
                                   adapter: RecyclerViewAdapter, recyclerView: RecyclerView) {
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

    // Check user fields, return true if all fields are filled
    private fun checkFields(keyword: String, city: String): Boolean {
        // Check if keyword and city are empty, make call to showAlert()
        if (keyword.isEmpty() && city.isEmpty()) {
            showAlert("Both Keyword and City are empty", "Please enter a keyword and city to search for events")
            return false
        }
        else if (keyword.isEmpty()) {
            showAlert("Keyword is empty", "Please enter a keyword to search for events")
            return false
        }
        else if (city.isEmpty()) {
            showAlert("City is empty", "Please enter a city to search for events")
            return false
        }
        else
            return true
    }

    private fun checkFieldsNearMe(keyword: String): Boolean {
        // Check if keyword is empty, make call to showAlert()
        if (keyword.isEmpty()) {
            showAlert("Keyword is empty", "Please enter a keyword to search for events")
            return false
        }
        else
            return true
    }

    // called on button click, hides the keyboard
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    // Function to get the username from firestore database and set the welcome text
    private fun setUser(){
        val docRef = db.collection("userProfiles").document(currentUser?.uid.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val username = documentSnapshot.getString("username")
            findViewById<TextView>(R.id.welcome_textView).text = "Welcome, $username!"
        }
    }

    // Helper function to show an alert dialog
    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Accept") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    // Clears user data and returns to the main activity
    fun signOut (view: View) {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
}