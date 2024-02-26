package com.example.intentwithcomplexdata
import java.io.Serializable

// Define a data class representing a User
data class User(
    val firstName: String, // First name of the user
    val lastName: String,  // Last name of the user
    val age: Int           // Age of the user
): Serializable          // Implement the Serializable interface for object serialization
