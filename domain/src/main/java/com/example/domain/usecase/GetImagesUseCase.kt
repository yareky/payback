package com.example.domain.usecase

import com.example.domain.model.Hit
import com.example.domain.model.Result
import com.example.domain.model.Status
import com.example.domain.repository.IRepoCache
import com.example.domain.repository.IRepoSearch

class GetImagesUseCase(private val repoSearch: IRepoSearch, private val repoCache: IRepoCache) {
    suspend operator fun invoke(id: Long): Result<Hit> {
        return when (val cached = repoCache.searchImage(id)) {
            is Status.SUCCESS -> cached
            is Status.ERROR -> searchAndCache(id)
        }
    }

    private suspend fun searchAndCache(id: Long): Result<Hit> {
        return when (val result = repoSearch.searchImage(id)) {
            is Status.ERROR -> result
            is Status.SUCCESS -> {
                repoCache.putImages(listOf(result.value))
                result
            }
        }
    }
}