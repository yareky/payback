package com.example.payback.domain.model

sealed interface Result<out T>

sealed interface Status<out T> {
    data class SUCCESS<out T>(val value: T) : Status<T>, Result<T>
    data class ERROR(val throwable: Throwable) : Status<Nothing>, Result<Nothing>

    data object LOADING : Status<Nothing>
    data object EMPTY : Status<Nothing>
}

fun <T> Result<T>.asStatus(): Status<T> = when (this) {
    is Status.ERROR -> Status.ERROR(throwable)
    is Status.SUCCESS -> Status.SUCCESS(value)
}
