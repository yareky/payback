package com.example.payback.data.extension

import com.example.payback.data.exception.ApiServiceException
import com.example.payback.domain.model.Result
import com.example.payback.domain.model.Status
import okhttp3.ResponseBody
import retrofit2.Response

fun <T, R> Response<T>.toResult(
    onSuccess: (T?) -> Result<R>,
    onError: (code: Int, message: String, errorBody: ResponseBody?) -> Result<R> = ::defaultErrorFun
): Result<R> {
    return if (isSuccessful) {
        onSuccess(body())
    } else {
        onError(code(), message(), errorBody())
    }
}

fun <T, R> Response<T>.toResult(onSuccess: (T?) -> Result<R>): Result<R> =
    toResult(onSuccess, ::defaultErrorFun)

@Suppress("UnusedPrivateMember")
private fun <R> defaultErrorFun(code: Int, message: String, errorBody: ResponseBody?): Result<R> =
    Status.ERROR(ApiServiceException(code, errorBody?.string()))
