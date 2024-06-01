package com.example.domain.repository

import com.example.domain.model.Hit
import com.example.domain.model.SearchImagesResponse

interface IRepoCache : IRepoSearch {
    suspend fun putImages(hits : List<Hit>)
    suspend fun cacheQuery(query: String, searchImagesResponse: SearchImagesResponse)
}