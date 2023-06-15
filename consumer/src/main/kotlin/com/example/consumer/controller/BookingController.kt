package com.example.consumer.controller

import BookingSpecificationBuilder
import com.example.consumer.dto.BookingSearchDto
import com.example.consumer.service.BookingService
import com.example.consumer.utils.ApiResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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
import java.util.*

@RestController
@RequestMapping("/api/bookings")
class BookingController(
    private val bookingService: BookingService
) {

    @GetMapping(value = ["/{id}"])
    fun findById(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        val apiResponse = ApiResponse()
        apiResponse.data = bookingService.findById(id)
        apiResponse.responseCode = HttpStatus.OK
        apiResponse.message = "Successfully found booking record"

        return ResponseEntity(apiResponse, apiResponse.responseCode !!)
    }


    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        val apiResponse = ApiResponse()
        apiResponse.data = bookingService.delete(id)
        apiResponse.responseCode = HttpStatus.OK
        apiResponse.message = "Successfully deleted booking record"

        return ResponseEntity(apiResponse, apiResponse.responseCode !!)
    }

    @PostMapping("/search")
    fun searchBookings(
        @RequestParam(name = "pageNum", defaultValue = "0") pageNum: Int,
        @RequestParam(name = "pageSize", defaultValue = "10") pageSize: Int,
        @RequestBody bookingSearchDto: BookingSearchDto
    ): ResponseEntity<ApiResponse> {
        val builder = BookingSpecificationBuilder()
        val criteriaList = bookingSearchDto.searchCriteriaList

        criteriaList.forEach { criteria ->
            criteria.dataOption = bookingSearchDto.dataOption
            builder.with(criteria)
        }

        val page = PageRequest.of(
            pageNum, pageSize,
            Sort.by("passengerName").ascending()
                .and(Sort.by("passengerContactNumber").ascending())
        )

        val apiResponse = ApiResponse()
        apiResponse.data = bookingService.findBySearchCriteria(builder.build(), page)
        apiResponse.responseCode = HttpStatus.OK
        apiResponse.message = "Successfully retrieved booking record"

        return ResponseEntity(apiResponse, apiResponse.responseCode !!)
    }

}