package com.example.consumer.utils


enum class SearchOperation {
    CONTAINS,
    DOES_NOT_CONTAIN,
    EQUAL,
    NOT_EQUAL,
    BEGINS_WITH,
    DOES_NOT_BEGIN_WITH,
    ENDS_WITH,
    DOES_NOT_END_WITH,
    NULL,
    NOT_NULL,
    GREATER_THAN,
    GREATER_THAN_EQUAL,
    LESS_THAN,
    LESS_THAN_EQUAL,
    ANY,
    ALL;

    companion object {
        val SIMPLE_OPERATION_SET = arrayOf(
            "cn", "nc", "eq", "ne", "bw", "bn", "ew",
            "en", "nu", "nn", "gt", "ge", "lt", "le"
        )

        fun getDataOption(dataOption: String?): SearchOperation? {
            return when (dataOption) {
                "all" -> ALL
                "any" -> ANY
                else -> null
            }
        }

        fun getSimpleOperation(
            input: String?
        ): SearchOperation? {
            return when (input) {
                "cn" -> CONTAINS
                "nc" -> DOES_NOT_CONTAIN
                "eq" -> EQUAL
                "ne" -> NOT_EQUAL
                "bw" -> BEGINS_WITH
                "bn" -> DOES_NOT_BEGIN_WITH
                "ew" -> ENDS_WITH
                "en" -> DOES_NOT_END_WITH
                "nu" -> NULL
                "nn" -> NOT_NULL
                "gt" -> GREATER_THAN
                "ge" -> GREATER_THAN_EQUAL
                "lt" -> LESS_THAN
                "le" -> LESS_THAN_EQUAL
                else -> null
            }
        }
    }
}