package com.example.consumer.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate


data class BookingDto(
    @JsonProperty("id")
    val id: Long?,

    @JsonProperty("passengerName")
    @NotBlank(message = "Passenger name is mandatory")
    val passengerName: String,

    @JsonProperty("passengerContactNumber")
    @NotBlank(message = "Passenger contact number is mandatory")
    val passengerContactNumber: String,

    @JsonProperty("fromDate")
    @NotNull(message = "From date is mandatory")
    val fromDate: LocalDate,

    @JsonProperty("toDate")
    @NotNull(message = "To date is mandatory")
    val toDate: LocalDate,

    @JsonProperty("numberOfPassengers")
    @NotNull(message = "Number of passengers is mandatory")
    val numberOfPassengers: Int,

    @JsonProperty("price")
    @NotNull(message = "Price is mandatory")
    val price: BigDecimal,

    @JsonProperty("rating")
    @NotNull(message = "Rating is mandatory")
    val rating: Double,

    @JsonProperty("tripWayPoints")
    @NotNull(message = "Trip waypoints are mandatory")
    val tripWayPoints:  List<TripWayPointDto>,
)