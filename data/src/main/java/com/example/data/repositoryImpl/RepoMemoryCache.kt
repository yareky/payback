package com.example.data.repositoryImpl

class RepoMemoryCache : com.example.domain.repository.IRepoCache {
    private val hitsMap = mutableMapOf<Long, com.example.domain.model.Hit>()
    private val queryMap = mutableMapOf<String, List<Long>>()

    override suspend fun putImages(hits: List<com.example.domain.model.Hit>) =
        hitsMap.putAll(hits.associateBy { it.id })

    override suspend fun cacheQuery(
        query: String,
        searchImagesResponse: com.example.domain.model.SearchImagesResponse
    ) {
        if (searchImagesResponse.hits.isNotEmpty()) {
            putImages(searchImagesResponse.hits)
            queryMap[query] = searchImagesResponse.hits.map { it.id }
        }
    }

    override suspend fun searchImages(query: String): com.example.domain.model.Result<com.example.domain.model.SearchImagesResponse> {
        return queryMap[query]?.let { ids ->
            ids.mapNotNull { hitsMap[it] }.let { hits ->
                if (hits.isNotEmpty()) {
                    com.example.domain.model.SearchImagesResponse(hits.size, hits.size, hits)
                } else {
                    null
                }
            }
        }?.let { com.example.domain.model.Status.SUCCESS(it) }
            ?: com.example.domain.model.Status.ERROR(
                NO_SUCH_ELEMENT_EXCEPTION
            )
    }

    override suspend fun searchImage(imageId: Long): com.example.domain.model.Result<com.example.domain.model.Hit> =
        hitsMap[imageId]?.let { com.example.domain.model.Status.SUCCESS(it) }
            ?: com.example.domain.model.Status.ERROR(
                NO_SUCH_ELEMENT_EXCEPTION
            )

    private companion object {
        val NO_SUCH_ELEMENT_EXCEPTION = NoSuchElementException()
        val EMPTY_RESPONSE = com.example.domain.model.SearchImagesResponse(0, 0, emptyList())
    }
}