package com.example.homework_4

import com.google.gson.annotations.SerializedName

data class UserData(
    val results: List<User>
)

data class User(
    val gender: String,
    val name: Names,
    val email: String,
    // If you want to give a different variable name (different than what is in the received data),
    // you need to use @SerializedName, otherwise you can simply give the same variable name
    @SerializedName("picture") val imageUrl: Picture
)

data class Names(
    val title: String,
    val first: String,
    val last: String
)

data class Picture(
    val medium: String
)