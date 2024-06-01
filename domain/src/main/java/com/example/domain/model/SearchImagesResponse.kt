package com.example.domain.model


data class SearchImagesResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<Hit>,
)