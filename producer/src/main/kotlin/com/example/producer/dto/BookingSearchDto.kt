package com.example.producer.dto

import com.example.producer.utils.SearchCriteria

data class BookingSearchDto(
    val searchCriteriaList: List<SearchCriteria>,
    val dataOption: String
)