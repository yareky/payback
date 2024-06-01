package com.example.data.mapper

import com.example.data.model.Hit
import com.example.data.model.SearchImagesResponse

internal fun SearchImagesResponse?.toDomainWithException(): com.example.domain.model.SearchImagesResponse {
    requireNotNull(this)
    return com.example.domain.model.SearchImagesResponse(
        hits = hits.toDomainWithException(),
        total = total,
        totalHits = totalHits,
    )
}

internal fun List<Hit>?.toDomainWithException(): List<com.example.domain.model.Hit> {
    requireNotNull(this)
    return map {
        it.toDomainWithException()
    }
}

internal fun Hit?.toDomainWithException(): com.example.domain.model.Hit {
    requireNotNull(this)
    return com.example.domain.model.Hit(
        comments = comments,
        downloads = downloads,
        id = id,
        largeImageUrl = largeImageUrl,
        likes = likes,
        previewUrl = previewUrl,
        tags = tags,
        user = user,
    )
}
