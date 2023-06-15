package com.example.producer.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate


data class BookingDto(
    @JsonProperty("id")
    private val id: Long?,

    @JsonProperty("passengerName")
    @NotBlank(message = "Passenger name is mandatory")
    private val passengerName: String,

    @JsonProperty("passengerContactNumber")
    @NotBlank(message = "Passenger contact number is mandatory")
    private val passengerContactNumber: String,

    @JsonProperty("fromDate")
    @NotNull(message = "From date is mandatory")
    private val fromDate: LocalDate,

    @JsonProperty("toDate")
    @NotNull(message = "To date is mandatory")
    private val toDate: LocalDate,

    @JsonProperty("numberOfPassengers")
    @NotNull(message = "Number of passengers is mandatory")
    private val numberOfPassengers: Int,

    @JsonProperty("price")
    @NotNull(message = "Price is mandatory")
    private val price: BigDecimal,

    @JsonProperty("rating")
    @NotNull(message = "Rating is mandatory")
    private val rating: Double,

    @JsonProperty("tripWayPoints")
    @NotNull(message = "Trip waypoints are mandatory")
    private val tripWayPoints:  List<TripWayPointDto>,
)