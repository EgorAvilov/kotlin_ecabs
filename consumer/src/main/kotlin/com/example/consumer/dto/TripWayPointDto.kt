package com.example.consumer.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TripWayPointDto(
    @JsonProperty("id")
    val id: Long?,

    @JsonProperty("locality")
    val locality: String,

    @JsonProperty("latitude")
    val latitude: Float,

    @JsonProperty("longitude")
    val longitude: Float
)