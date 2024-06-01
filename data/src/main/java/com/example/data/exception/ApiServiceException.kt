package com.example.data.exception

data class ApiServiceException(val code: Int, val error: String?) : Exception()