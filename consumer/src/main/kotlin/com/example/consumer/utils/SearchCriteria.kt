package com.example.consumer.utils

data class SearchCriteria(
    var filterKey: String? = null,
    var value: Any? = null,
    var operation: String? = null,
    var dataOption: String? = null
) {
    constructor(filterKey: String, operation: String, value: Any) : this(filterKey, value, operation, null)
}
