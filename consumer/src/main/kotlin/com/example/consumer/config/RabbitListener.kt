package com.example.consumer.config

import com.example.consumer.dto.BookingDto
import com.example.consumer.service.BookingService
import com.example.consumer.utils.ApiResponse
import com.example.consumer.utils.LocalDateAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import jakarta.annotation.PostConstruct
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.time.LocalDate


@Component
class RabbitListener(
    private val bookingService: BookingService,
    private var gson: Gson
) {
    @PostConstruct
    fun init() {
        gson = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter()).create()
    }

    @RabbitListener(queues = [BOOKING_CREATE_UPDATE_QUEUE_NAME])
    fun processQueueCreateUpdateBooking(message: String): String {
        val apiResponse = ApiResponse()
        apiResponse.data = bookingService.createUpdate(gson.fromJson(message, BookingDto::class.java))
        apiResponse.responseCode = HttpStatus.OK
        apiResponse.message = "Successfully created/updated booking record"

        return gson.toJson(apiResponse)
    }

    @RabbitListener(queues = [BOOKING_FIND_ALL_QUEUE_NAME])
    fun processQueueFindAllBookings(message: String): String {
        val pageMap = message.split(", ").associate {
            val (left, right) = it.split("=")
            left to right.toInt()
        }
        val page = PageRequest.of(
            pageMap["pageNum"] !!, pageMap["pageSize"] !!,
            Sort.by("passengerName").ascending()
                .and(Sort.by("passengerContactNumber").ascending())
        )
        val apiResponse = ApiResponse()
        apiResponse.data = bookingService.findAll(page)
        apiResponse.responseCode = HttpStatus.OK
        apiResponse.message = "Successfully found all booking records"

        return gson.toJson(apiResponse)
    }

    @RabbitListener(queues = [MESSAGE_AUDIT_QUEUE_NAME])
    fun processQueueMessageAudit(message: String) = println("Received from queue $MESSAGE_AUDIT_QUEUE_NAME : $message")

}

const val BOOKING_CREATE_UPDATE_QUEUE_NAME: String = "BookingCreateUpdateQueue"
const val BOOKING_FIND_ALL_QUEUE_NAME: String = "BookingFindAllQueue"
const val MESSAGE_AUDIT_QUEUE_NAME: String = "MessageAuditQueue"
