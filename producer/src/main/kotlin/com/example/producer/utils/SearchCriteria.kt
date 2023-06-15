package com.example.producer.utils

data class SearchCriteria(
    var filterKey: String? = null,
    var value: Any? = null,
    var operation: String? = null,
    var dataOption: String? = null
)
