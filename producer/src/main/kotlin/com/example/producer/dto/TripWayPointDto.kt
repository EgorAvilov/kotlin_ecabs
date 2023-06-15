package com.example.producer.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TripWayPointDto(
    @JsonProperty("id")
    private val id: Long?,

    @JsonProperty("locality")
    private val locality: String,

    @JsonProperty("latitude")
    private val latitude: Float,

    @JsonProperty("longitude")
    private val longitude: Float
)