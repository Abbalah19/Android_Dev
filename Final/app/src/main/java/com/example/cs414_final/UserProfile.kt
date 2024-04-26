package com.example.cs414_final
/*
userProfiles -
      |
      |
      ->userProfiles -> email, username
             |
             |
             ->savedEvents -
                   |
                   |
                   ->EventName -> date(String), eventImage(URL String), eventName(String), location(String), priceRange(String), eventUrl(String)
 */
data class UserProfile(
    val email: String?,
    val username: String?,
    val savedEvents: List<SavedEventData>? = null
)

data class SavedEventData(
    var documentId: String? = null,
    var eventImage: String? = null,
    var eventName: String? = null,
    var location: String? = null,
    var date: String? = null,
    var priceRange: String? = null,
    var eventUrl: String? = null,
)

