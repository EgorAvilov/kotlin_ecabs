package com.example.consumer.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "trip_way_points")
data class TripWayPoint(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "locality")
    val locality: String,

    @Column(name = "latitude")
    val latitude: Float,

    @Column(name = "longitude")
    val longitude: Float
)