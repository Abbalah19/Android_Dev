package com.example.cs414_final

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketMasterService {
    @GET("events.json") // the part after the base URL before the query parameters
    fun getEvents(@Query("apikey") apikey: String,
                  @Query("keyword") keyword: String,
                  @Query("city") city: String,
                  @Query("sort") sort: String) : Call<EventsList> // sort is hardcoded to "date,asc"
}