package com.example.data.repositoryImpl

import com.example.data.api.ApiPixBay
import com.example.data.extension.toResult
import com.example.data.mapper.toDomainWithException
import java.net.URLEncoder

internal class RepoSearch(private val apiPixBay: ApiPixBay) :
    com.example.domain.repository.IRepoSearch {

    override suspend fun searchImages(query: String): com.example.domain.model.Result<com.example.domain.model.SearchImagesResponse> {
        return try {
            apiPixBay.searchImages(URLEncoder.encode(query, ENCODER)).toResult {
                com.example.domain.model.Status.SUCCESS(it.toDomainWithException())
            }
        } catch (exception: Exception) {
            com.example.domain.model.Status.ERROR(exception)
        }
    }

    override suspend fun searchImage(imageId: Long): com.example.domain.model.Result<com.example.domain.model.Hit> {
        return try {
            apiPixBay.searchImage(imageId).toResult {
                com.example.domain.model.Status.SUCCESS(
                    it?.hits?.firstOrNull().toDomainWithException()
                )
            }
        } catch (exception: Exception) {
            com.example.domain.model.Status.ERROR(exception)
        }
    }

    companion object {
        private const val ENCODER = "UTF-8"
    }
}
