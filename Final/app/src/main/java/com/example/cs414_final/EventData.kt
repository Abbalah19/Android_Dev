package com.example.cs414_final

data class EventsList (
    val _embedded: Embedded
)

data class Embedded (
    val events: List<Event>
)

data class Event (
    val name: String?,
    val url: String,
    val images: List<Image>,
    val dates: Dates,
    val priceRanges: List<PriceRange>,
    val _embedded: EmbeddedVenues
) {
}

data class Image (
    val ratio: String,
    val url: String,
    val width: Int,
    val height: Int,
    val fallback: Boolean,
)

data class Dates (
    val start: Start,
)

data class Start (
    val localDate: String?,
    val localTime: String?,
    val dateTime: String?,
)

data class PriceRange (
    val type: String,
    val currency: String,
    val min: Double?,
    val max: Double?,
)

data class EmbeddedVenues (
    val venues: List<Venue>
)

data class Venue (
    val name: String?,
    val url: String?,
    val city: City?,
    val state: State?,
    val country: Country?,
    val address: Address?
)

data class City (
    val name: String
)

data class State (
    val name: String,
    val stateCode: String
)

data class Country (
    val name: String,
    val countryCode: String
)

data class Address (
    val line1: String
)



/*

        _embedded:
            events:
                0:
                    name: "Train & Reo Speedwagon- Summer Road Trip 2024"
                    type: "event"
                    id: "G5vVZbdQPiZYR"
                    test: false

 */