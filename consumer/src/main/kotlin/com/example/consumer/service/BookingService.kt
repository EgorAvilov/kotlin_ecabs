package com.example.consumer.service

import com.example.consumer.data.Booking
import com.example.consumer.dto.BookingDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

interface BookingService {
    fun createUpdate(bookingDto: BookingDto): BookingDto
    fun delete(id: Long)
    fun findById(id: Long): BookingDto
    fun findAll(page: Pageable): Page<BookingDto>
    fun findBySearchCriteria(spec: Specification<Booking>, page: Pageable): Page<BookingDto>
}