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

private const val TAG = "MainActivity"
private const val API_KEY = "Qvr3MbRtSApocNPL6TbfDGdHnlm087nD"

class MainActivity : AppCompatActivity() {

    private val root_URL = "https://app.ticketmaster.com/discovery/v2/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userList = ArrayList<User>()
        val adapter = TicketAdapter(userList)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.adapter = adapter
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
                Log.d(TAG, "Error: ${t.message}")
            }
        })
    }
}