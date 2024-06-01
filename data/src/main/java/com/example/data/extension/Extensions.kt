package com.example.data.extension

import com.example.data.exception.ApiServiceException
import okhttp3.ResponseBody
import retrofit2.Response

fun <T, R> Response<T>.toResult(
    onSuccess: (T?) -> com.example.domain.model.Result<R>,
    onError: (code: Int, message: String, errorBody: ResponseBody?) -> com.example.domain.model.Result<R> = ::defaultErrorFun
): com.example.domain.model.Result<R> {
    return if (isSuccessful) {
        onSuccess(body())
    } else {
        onError(code(), message(), errorBody())
    }
}

fun <T, R> Response<T>.toResult(onSuccess: (T?) -> com.example.domain.model.Result<R>): com.example.domain.model.Result<R> =
    toResult(onSuccess, ::defaultErrorFun)

@Suppress("UnusedPrivateMember")
private fun <R> defaultErrorFun(
    code: Int,
    message: String,
    errorBody: ResponseBody?
): com.example.domain.model.Result<R> =
    com.example.domain.model.Status.ERROR(ApiServiceException(code, errorBody?.string()))
