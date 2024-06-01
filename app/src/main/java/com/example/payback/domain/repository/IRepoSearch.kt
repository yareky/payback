package com.example.payback.domain.repository

import com.example.payback.domain.model.Hit
import com.example.payback.domain.model.Result
import com.example.payback.domain.model.SearchImagesResponse

interface IRepoSearch {
    suspend fun searchImages(query: String): Result<SearchImagesResponse>
    suspend fun searchImage(imageId: Long): Result<Hit>
}