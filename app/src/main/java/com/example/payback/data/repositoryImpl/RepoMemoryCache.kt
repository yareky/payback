package com.example.payback.data.repositoryImpl

import com.example.payback.domain.model.Hit
import com.example.payback.domain.model.Result
import com.example.payback.domain.model.SearchImagesResponse
import com.example.payback.domain.model.Status
import com.example.payback.domain.repository.IRepoCache

class RepoMemoryCache : IRepoCache {
    private val hitsMap = mutableMapOf<Long, Hit>()
    private val queryMap = mutableMapOf<String, List<Long>>()

    override suspend fun putImages(hits: List<Hit>) = hitsMap.putAll(hits.associateBy { it.id })

    override suspend fun cacheQuery(query: String, searchImagesResponse: SearchImagesResponse) {
        if (searchImagesResponse.hits.isNotEmpty()) {
            putImages(searchImagesResponse.hits)
            queryMap[query] = searchImagesResponse.hits.map { it.id }
        }
    }

    override suspend fun searchImages(query: String): Result<SearchImagesResponse> {
        return queryMap[query]?.let { ids ->
            ids.mapNotNull { hitsMap[it] }.let { hits ->
                if (hits.isNotEmpty()) {
                    SearchImagesResponse(hits.size, hits.size, hits)
                } else {
                    null
                }
            }
        }?.let { Status.SUCCESS(it) } ?: Status.ERROR(NO_SUCH_ELEMENT_EXCEPTION)
    }

    override suspend fun searchImage(imageId: Long): Result<Hit> =
        hitsMap[imageId]?.let { Status.SUCCESS(it) } ?: Status.ERROR(NO_SUCH_ELEMENT_EXCEPTION)

    private companion object {
        val NO_SUCH_ELEMENT_EXCEPTION = NoSuchElementException()
        val EMPTY_RESPONSE = SearchImagesResponse(0, 0, emptyList())
    }
}