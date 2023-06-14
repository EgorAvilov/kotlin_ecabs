package com.example.producer.controller

import com.example.producer.config.CREATE_UPDATE_BOOKING_KEY
import com.example.producer.config.FIND_ALL_BOOKING_KEY
import com.example.producer.config.MESSAGE_EXCHANGE
import com.example.producer.dto.BookingDto
import com.google.gson.Gson
import jakarta.annotation.PostConstruct
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate


@RestController
@RequestMapping("/api/bookings")
class BookingController(
    private val rabbitTemplate: RabbitTemplate,
    private val gson: Gson,
    private val restTemplate: RestTemplate
) {

    @PostConstruct
    fun init() {
        rabbitTemplate.setExchange(MESSAGE_EXCHANGE)
    }

    @PostMapping
    fun create(@RequestBody booking: BookingDto): ResponseEntity<Any> = ResponseEntity<Any>(
        rabbitTemplate.convertSendAndReceive(
            CREATE_UPDATE_BOOKING_KEY,
            gson.toJson(booking)
        ), HttpStatus.OK
    )

    @GetMapping
    fun findAll(): ResponseEntity<Any> = ResponseEntity<Any>(
        rabbitTemplate.convertSendAndReceive(
            FIND_ALL_BOOKING_KEY,
            "get-all-booking"
        ), HttpStatus.OK
    )

    @GetMapping(value = ["/{id}"])
    fun findById(@PathVariable id: Long): ResponseEntity<Any> {
        val headers = HttpHeaders()
        headers["Accept"] = "application/json"
        val request = HttpEntity<String>(headers)
        return restTemplate.exchange(
            "http://localhost:8081/api/bookings/{id}",
            HttpMethod.GET,
            request,
            Any::class.java,
            id
        )
    }

    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        val headers = HttpHeaders()
        headers["Accept"] = "application/json"
        val request = HttpEntity<String>(headers)
        return restTemplate.exchange(
            "http://localhost:8081/api/bookings/{id}",
            HttpMethod.DELETE,
            request,
            Any::class.java,
            id
        )
    }

}