package com.example.data.repositoryImpl

import com.example.data.api.ApiPixBay
import com.example.data.extension.toResult
import com.example.data.mapper.toDomainWithException
import com.example.domain.model.Hit
import com.example.domain.model.Result
import com.example.domain.model.SearchImagesResponse
import com.example.domain.model.Status
import com.example.domain.repository.IRepoSearch
import java.net.URLEncoder

internal class RepoSearch(private val apiPixBay: ApiPixBay) :
    IRepoSearch {

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
                Status.SUCCESS(
                    it?.hits?.firstOrNull().toDomainWithException()
                )
            }
        } catch (exception: Exception) {
            Status.ERROR(exception)
        }
    }

    companion object {
        private const val ENCODER = "UTF-8"
    }
}
