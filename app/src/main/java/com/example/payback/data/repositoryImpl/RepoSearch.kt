package com.example.payback.data.repositoryImpl

import com.example.payback.data.api.ApiPixBay
import com.example.payback.data.extension.toResult
import com.example.payback.data.mapper.toDomainWithException
import com.example.payback.domain.model.Hit
import com.example.payback.domain.model.Result
import com.example.payback.domain.model.SearchImagesResponse
import com.example.payback.domain.model.Status
import com.example.payback.domain.repository.IRepoSearch
import java.net.URLEncoder

internal class RepoSearch(private val apiPixBay: ApiPixBay) : IRepoSearch {

    override suspend fun searchImages(query: String): Result<SearchImagesResponse> {
        return try {
            apiPixBay.searchImages(URLEncoder.encode(query, ENCODER)).toResult {
                Status.SUCCESS(it.toDomainWithException())
            }
        } catch (exception: Exception) {
            Status.ERROR(exception)
        }
    }

    override suspend fun searchImage(imageId: Long): Result<Hit> {
        return try {
            apiPixBay.searchImage(imageId).toResult {
                Status.SUCCESS(it?.hits?.firstOrNull().toDomainWithException())
            }
        } catch (exception: Exception) {
            Status.ERROR(exception)
        }
    }

    companion object {
        private const val ENCODER = "UTF-8"
    }
}
