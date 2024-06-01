package com.example.payback.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val httpUrl = originalRequest.url.newBuilder().addQueryParameter("key", apiKey).build()

        val request = originalRequest.newBuilder().url(httpUrl).build()

        return chain.proceed(request)
    }
}