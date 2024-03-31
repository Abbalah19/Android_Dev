package com.example.homework_4

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketMasterService {

    @GET("events.json")
    fun getEvents(@Query("Qvr3MbRtSApocNPL6TbfDGdHnlm087nD") apiKey: String): Call<String>
}
/*
    // Requesting Multiple Users, https://randomuser.me/api/?results=5000
    @GET(".")
    fun getMultipleUserInfo(@Query("results") amount: Int) : Call<UserData>


    // Requesting Multiple Users, https://randomuser.me/api/?results=5000&nat=us
    @GET(".")
    fun getMultipleUserInfoWithNationality(@Query("results") amount: Int,
                                           @Query("nat") nationality: String) : Call<UserData>
}
 */