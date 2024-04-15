package com.example.a031824

data class UserData(
    val result: List<User>
)

data class User(
    val gender: String,
    val name: NameData,
    val email: String,

    @serializedName("picture") val imageURL: PictureData
)

data class NameData(
    val title: String,
    val first: String,
    val last: String
)

data class PictureData(
    val medium: String
)