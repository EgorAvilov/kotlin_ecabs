package com.example.consumer.service.impl

import com.example.consumer.data.Booking
import com.example.consumer.dto.BookingDto
import com.example.consumer.mapper.BookingMapper
import com.example.consumer.repository.BookingRepository
import com.example.consumer.service.BookingService
import com.google.gson.Gson
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.jvm.optionals.getOrElse

@Service
class BookingServiceImpl(
    private val bookingRepository: BookingRepository,
    private val bookingMapper: BookingMapper
) : BookingService {
    override fun createUpdate(bookingDto: BookingDto): BookingDto {
        bookingDto.id?.let {
            return update(bookingDto);
        } ?: run {
            return create(bookingDto)
        }
    }

    override fun delete(id: Long): Unit = bookingRepository.deleteById(id)

    override fun findById(id: Long): BookingDto {
        val booking =
            bookingRepository.findById(id).getOrElse { throw IllegalArgumentException("No booking with such id $id") }
        return bookingMapper.map(booking)
    }

    override fun findAll(): List<BookingDto> {
        val bookings = bookingRepository.findAll()
        return bookings.map { bookingMapper.map(it) }
    }

    fun create(bookingDto: BookingDto): BookingDto {
        var booking: Booking = bookingMapper.map(bookingDto)
        booking.createdAt = Instant.now()
        booking.updatedAt = Instant.now()
        booking = bookingRepository.save(booking)
        return bookingMapper.map(booking)
    }

    fun update(bookingDto: BookingDto): BookingDto {
        val booking: Booking = bookingMapper.map(bookingDto)
        var persistBooking = bookingRepository.findById(booking.id)
            .getOrElse { throw IllegalArgumentException("No booking with such id ${booking.id}") }
        bookingMapper.update(booking, persistBooking)
        persistBooking.updatedAt = Instant.now()
        persistBooking = bookingRepository.save(persistBooking)
        return bookingMapper.map(persistBooking)
    }
}