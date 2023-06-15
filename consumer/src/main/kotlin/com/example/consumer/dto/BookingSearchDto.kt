package com.example.consumer.dto

import com.example.consumer.utils.SearchCriteria

data class BookingSearchDto(
    val searchCriteriaList: List<SearchCriteria>,
    val dataOption: String
)