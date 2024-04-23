package com.example.cs414_final

/*
val savedEvent = SavedEventData().apply {
    this.name = currentItem.name
    this.dates = currentItem.dates
    this.priceRanges = currentItem.priceRanges
    this._embedded = currentItem._embedded
    this.url = currentItem.url
    this.images = currentItem.images
}
 */
data class UserProfile(
    val email: String?,
    val username: String?,
    val savedEvents: List<SavedEventData>? = null
)

class SavedEventData(
    var eventImage: String? = null,
    var eventName: String? = null,
    var loaction: String? = null,
    var date: String? = null,
    var priceRange: String? = null,
)
