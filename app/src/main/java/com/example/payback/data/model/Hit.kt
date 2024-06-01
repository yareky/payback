package com.example.payback.data.model

import com.google.gson.annotations.SerializedName

internal data class Hit(
    @SerializedName("id") val id: Long,
    @SerializedName("previewURL") val previewUrl: String,
    @SerializedName("largeImageURL") val largeImageUrl: String,
    @SerializedName("user") val user: String,
    @SerializedName("likes") val likes: Int,
    @SerializedName("comments") val comments: Int,
    @SerializedName("downloads") val downloads: Int,
    @SerializedName("tags") val tags: String,
)