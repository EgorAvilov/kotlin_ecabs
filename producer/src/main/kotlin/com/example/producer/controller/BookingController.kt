package com.example.producer.controller

import com.example.producer.config.CREATE_UPDATE_BOOKING_KEY
import com.example.producer.config.FIND_ALL_BOOKING_KEY
import com.example.producer.config.MESSAGE_EXCHANGE
import com.example.producer.dto.BookingDto
import com.example.producer.dto.BookingSearchDto
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder


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
    fun findAll(
        @RequestParam(name = "pageNum", defaultValue = "0") pageNum: Int,
        @RequestParam(name = "pageSize", defaultValue = "10") pageSize: Int,
    ): ResponseEntity<Any> {
        val params = mapOf("pageNum" to pageNum, "pageSize" to pageSize)
        return ResponseEntity<Any>(
            rabbitTemplate.convertSendAndReceive(
                FIND_ALL_BOOKING_KEY,
                params.toString().removePrefix("{").removeSuffix("}")
            ), HttpStatus.OK
        )
    }

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

    /**
     * Method for search bookings by any existing param
     * Example:
     * {
     *     "dataOption":"all",
     *     "searchCriteriaList":[
     *        {
     *           "filterKey":"passengerName",
     *           "operation":"cn",
     *           "value":"r"
     *        },
     *         {
     *           "filterKey":"rating",
     *           "operation":"lt",
     *           "value":4.7
     *        }
     *     ]
     * } where:
     *    dataOption: String = "all" - uses "AND" for predicated
     *                          "any" - uses "OR" for predicates
     *    searchCriteriaList: contains a list of search criteria and each search criteria has a “filterKey”, “operation” and “value”:
     *                filterKey: String = fieldName
     *                operation: String
     *                     "cn" -> CONTAINS
     *                     "nc" -> DOES_NOT_CONTAIN
     *                     "eq" -> EQUAL
     *                     "ne" -> NOT_EQUAL
     *                     "bw" -> BEGINS_WITH
     *                     "bn" -> DOES_NOT_BEGIN_WITH
     *                     "ew" -> ENDS_WITH
     *                     "en" -> DOES_NOT_END_WITH
     *                     "nu" -> NULL
     *                     "nn" -> NOT_NULL
     *                     "gt" -> GREATER_THAN
     *                     "ge" -> GREATER_THAN_EQUAL
     *                     "lt" -> LESS_THAN
     *                     "le" -> LESS_THAN_EQUAL
     *                value: Any = searchValue
     */
    @PostMapping("/search")
    fun searchBookings(
        @RequestParam(name = "pageNum", defaultValue = "0") pageNum: Int,
        @RequestParam(name = "pageSize", defaultValue = "10") pageSize: Int,
        @RequestBody bookingSearchDto: BookingSearchDto
    ): ResponseEntity<Any> {
        val headers = HttpHeaders()
        headers["Accept"] = "application/json"
        val request = HttpEntity<BookingSearchDto>(bookingSearchDto, headers)
        val url = UriComponentsBuilder
            .fromHttpUrl("http://localhost:8081/api/bookings/search")
            .queryParam("pageNum", pageNum)
            .queryParam("pageSize", pageSize)
            .toUriString()
        return restTemplate.exchange(
            url,
            HttpMethod.POST,
            request,
            Any::class.java
        )
    }

}