package com.example.consumer.repository

import com.example.consumer.data.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookingRepository: JpaRepository<Booking, Long> {
}