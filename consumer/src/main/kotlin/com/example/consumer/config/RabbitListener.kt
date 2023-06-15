package com.example.consumer.config

import com.example.consumer.dto.BookingDto
import com.example.consumer.service.BookingService
import com.google.gson.Gson
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitListener(
    private val bookingService: BookingService,
    private val gson: Gson
) {
    @RabbitListener(queues = [BOOKING_CREATE_UPDATE_QUEUE_NAME])
    fun processQueueCreateUpdateBooking(message: String): String =
        gson.toJson(bookingService.createUpdate(gson.fromJson(message, BookingDto::class.java)))

    @RabbitListener(queues = [BOOKING_FIND_ALL_QUEUE_NAME])
    fun processQueueFindAllBookings(message: String): String = gson.toJson(bookingService.findAll())

    @RabbitListener(queues = [MESSAGE_AUDIT_QUEUE_NAME])
    fun processQueueMessageAudit(message: String) = println("Received from queue $MESSAGE_AUDIT_QUEUE_NAME : $message")

}

const val BOOKING_CREATE_UPDATE_QUEUE_NAME: String = "BookingCreateUpdateQueue"
const val BOOKING_FIND_ALL_QUEUE_NAME: String = "BookingFindAllQueue"
const val MESSAGE_AUDIT_QUEUE_NAME: String = "MessageAuditQueue"
