package com.example.consumer.service

import com.example.consumer.dto.BookingDto

interface BookingService {
    fun createUpdate(bookingDto: BookingDto): BookingDto
    fun delete(id: Long)
    fun findById(id: Long): BookingDto
    fun findAll(): List<BookingDto>

}