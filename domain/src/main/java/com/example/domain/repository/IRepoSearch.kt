package com.example.domain.repository

import com.example.domain.model.Hit
import com.example.domain.model.Result
import com.example.domain.model.SearchImagesResponse

interface IRepoSearch {
    suspend fun searchImages(query: String): Result<SearchImagesResponse>
    suspend fun searchImage(imageId: Long): Result<Hit>
}