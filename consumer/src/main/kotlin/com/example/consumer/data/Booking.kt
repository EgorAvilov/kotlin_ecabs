package com.example.consumer.data


import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate

@Entity
@Table(name = "bookings")
data class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "passenger_name", nullable = false)
    val passengerName: String?,

    @Column(name = "passenger_contact_number", nullable = false)
    val passengerContactNumber: String,

    @Column(name = "from_date", nullable = false)
    val fromDate: LocalDate,

    @Column(name = "to_date", nullable = false)
    val toDate: LocalDate,

    @Column(name = "number_of_passengers", nullable = false)
    val numberOfPassengers: Int,

    @Column(name = "price", nullable = false)
    val price: BigDecimal,

    @Column(name = "rating", nullable = false)
    val rating: Double,

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id")
    val tripWayPoints: List<TripWayPoint>,

    @Column(name = "createdAt", nullable = false)
    var createdAt: Instant?,

    @Column(name = "updatedAt", nullable = false)
    var updatedAt: Instant?,

)