package com.example.payback.domain.model


data class Hit(
    val id: Long,
    val previewUrl: String,
    val largeImageUrl: String,
    val user: String,
    val likes: Int,
    val comments: Int,
    val downloads: Int,
    val tags: String,
)