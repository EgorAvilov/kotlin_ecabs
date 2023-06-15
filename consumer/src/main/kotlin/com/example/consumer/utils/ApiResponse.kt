package com.example.consumer.utils

import org.springframework.http.HttpStatus

data class ApiResponse(
    var data: Any? = null,
    var message: String? = null,
    var responseCode: HttpStatus? = null
)
