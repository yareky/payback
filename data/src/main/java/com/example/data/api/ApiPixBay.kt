package com.example.data.api

import com.example.data.model.SearchImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiPixBay {
    @GET("/api/")
    suspend fun searchImages(
        @Query("q") query: String
    ): Response<SearchImagesResponse>

    @GET("/api/")
    suspend fun searchImage(@Query("id") imageId: Long): Response<SearchImagesResponse>
}