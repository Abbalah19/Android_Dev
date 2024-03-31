// 	key = Qvr3MbRtSApocNPL6TbfDGdHnlm087nD
// https://app.ticketmaster.com/discovery/v1/events.json?apikey=4dsfsf94tyghf85jdhshwge334
// need keyword, city, sort and api key
package com.example.homework_4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

private const val API_KEY = "Qvr3MbRtSApocNPL6TbfDGdHnlm087nD"

class MainActivity : AppCompatActivity() {

    private val root_URL = "https://app.ticketmaster.com/discovery/v2/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define an array to store a list of users -- this will be the list storing information
        // coming from the API
        val userList = ArrayList<User>()

        // specify a viewAdapter for the dataset
        val adapter = TicketAdapter(userList)

        // Store the the recyclerView widget in a variable
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.adapter = adapter

        // use a linear layout manager for the recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)



        val retrofit = Retrofit.Builder()
            .baseUrl(root_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val ticketMasterAPI = retrofit.create(TicketMasterService::class.java)


        val call = ticketMasterAPI.getEvents(API_KEY)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    File("response.json").writeText(responseBody)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("MainActivity", "Error: ${t.message}")
            }
        })
    }
}

            /*
        //ticketMasterAPI.getMultipleUserInfoWithNationality(20, "us").enqueue(object : Callback<UserData> {

            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                Log.d(TAG, "onResponse: $response")

                // Get access to the body with response.body().
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Valid response was not received")
                    return
                }

                // The following log messages are just for testing purpose
                // Normally we do not need to parse the data manually as the main benefit of using
                // "Gson converter factory" is to directly convert the response.body to a Kotlin object
                Log.d(TAG, ": ${body.results.get(0).name.first}")
                Log.d(TAG, ": ${body.results.get(0).name.last}")
                Log.d(TAG, ": ${body.results.get(0).email}")
                Log.d(TAG, ": ${body.results.get(0).gender}")
                Log.d(TAG, ": ${body.results.get(0).imageUrl.medium}")


                // Add all items from the API response (parsed using Gson) to the user list
                userList.addAll(body.results)
                // Update the adapter with the new data
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.d(TAG, "onFailure : $t")
            }

        })

    }
}

             */