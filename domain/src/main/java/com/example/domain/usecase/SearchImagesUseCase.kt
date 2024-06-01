package com.example.domain.usecase

import com.example.domain.model.Result
import com.example.domain.model.SearchImagesResponse
import com.example.domain.model.Status
import com.example.domain.repository.IRepoCache
import com.example.domain.repository.IRepoSearch

class SearchImagesUseCase(private val repoSearch: IRepoSearch, private val repoCache: IRepoCache) {
    suspend operator fun invoke(words: String): Result<SearchImagesResponse> {
        val cached = repoCache.searchImages(words)
        return when (cached) {
            is Status.SUCCESS -> cached
            is Status.ERROR -> searchAndCache(words)
        }
    }

    private suspend fun searchAndCache(words: String): Result<SearchImagesResponse> {
        val result = repoSearch.searchImages(words)
        return when (result) {
            is Status.ERROR -> result
            is Status.SUCCESS -> {
                repoCache.cacheQuery(words, result.value)
                result
            }
        }
    }
}