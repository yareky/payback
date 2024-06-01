package com.example.payback.domain.repository

import com.example.payback.domain.model.Hit
import com.example.payback.domain.model.SearchImagesResponse

interface IRepoCache : IRepoSearch {
    suspend fun putImages(hits : List<Hit>)
    suspend fun cacheQuery(query: String, searchImagesResponse: SearchImagesResponse)
}