package com.example.payback.data.model

import com.google.gson.annotations.SerializedName

internal data class SearchImagesResponse (
    @SerializedName("total") val total: Int,
    @SerializedName("totalHits") val totalHits: Int,
    @SerializedName("hits") val hits: List<Hit>,
)