package com.example.data.repositoryImpl

import com.example.domain.model.Hit
import com.example.domain.model.Result
import com.example.domain.model.SearchImagesResponse
import com.example.domain.model.Status
import com.example.domain.repository.IRepoCache

class RepoMemoryCache(
    private val hitsMap: MutableMap<Long, Hit> = mutableMapOf(),
    private val queryMap: MutableMap<String, List<Long>> = mutableMapOf()
) : IRepoCache {

    override suspend fun putImages(hits: List<Hit>) =
        hitsMap.putAll(hits.associateBy { it.id })

    override suspend fun cacheQuery(
        query: String,
        searchImagesResponse: SearchImagesResponse
    ) {
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
        }?.let { Status.SUCCESS(it) }
            ?: Status.ERROR(
                NO_SUCH_ELEMENT_EXCEPTION
            )
    }

    override suspend fun searchImage(imageId: Long): Result<Hit> =
        hitsMap[imageId]?.let { Status.SUCCESS(it) }
            ?: Status.ERROR(
                NO_SUCH_ELEMENT_EXCEPTION
            )

    private companion object {
        val NO_SUCH_ELEMENT_EXCEPTION = NoSuchElementException()
        val EMPTY_RESPONSE = SearchImagesResponse(0, 0, emptyList())
    }
}