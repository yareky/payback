package com.example.payback.domain.usecase

import com.example.payback.domain.model.Hit
import com.example.payback.domain.model.Result
import com.example.payback.domain.model.Status
import com.example.payback.domain.repository.IRepoCache
import com.example.payback.domain.repository.IRepoSearch

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