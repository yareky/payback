package com.example.data.mapper

import com.example.data.model.Hit
import com.example.data.model.SearchImagesResponse
import com.example.domain.model.Hit as HitDomain
import com.example.domain.model.SearchImagesResponse as SearchImagesResponseDomain

internal fun SearchImagesResponse?.toDomainWithException(): SearchImagesResponseDomain {
    requireNotNull(this)
    return SearchImagesResponseDomain(
        hits = hits.toDomainWithException(),
        total = total,
        totalHits = totalHits,
    )
}

internal fun List<Hit>?.toDomainWithException(): List<HitDomain> {
    requireNotNull(this)
    return map {
        it.toDomainWithException()
    }
}

internal fun Hit?.toDomainWithException(): HitDomain {
    requireNotNull(this)
    return HitDomain(
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
