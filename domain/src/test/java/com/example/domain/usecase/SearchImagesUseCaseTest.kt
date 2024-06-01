package com.example.domain.usecase

import com.example.domain.model.Hit
import com.example.domain.model.Result
import com.example.domain.model.SearchImagesResponse
import com.example.domain.model.Status
import com.example.domain.repository.IRepoCache
import com.example.domain.repository.IRepoSearch
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


class SearchImagesUseCaseTest {
    private val mockRepoCache: IRepoCache = mockk(relaxed = true)
    private val mockRepoSearch: IRepoSearch = mockk(relaxed = true)
    private val mockHit: Hit = mockk(relaxed = true)
    private val mockImagesResponse: SearchImagesResponse = mockk()

    @Test
    fun `invoke when no cached hits`() = runTest {
        coEvery { mockRepoCache.searchImages(any()) } returns Status.ERROR(EXCEPTION)
        coEvery { mockRepoSearch.searchImages(any()) } returns Status.SUCCESS(mockImagesResponse)
        val tested = SearchImagesUseCase(mockRepoSearch, mockRepoCache)

        val result: Result<SearchImagesResponse> = tested.invoke(QUERY)

        assert(result is Status.SUCCESS)
        val success = result as Status.SUCCESS
        Assert.assertEquals(mockImagesResponse, success.value)
        coVerify(exactly = 1) { mockRepoSearch.searchImages(QUERY) }
        coVerify(exactly = 1) { mockRepoCache.searchImages(QUERY) }
    }

    @Test
    fun `invoke when cached hits`() = runTest {
        coEvery { mockRepoCache.searchImages(QUERY) } returns Status.SUCCESS(mockImagesResponse)
        coEvery { mockRepoSearch.searchImages(QUERY) } returns Status.ERROR(EXCEPTION)
        val tested = SearchImagesUseCase(mockRepoSearch, mockRepoCache)

        val result: Result<SearchImagesResponse> = tested.invoke(QUERY)

        assert(result is Status.SUCCESS)
        val success = result as Status.SUCCESS
        Assert.assertEquals(mockImagesResponse, success.value)
        coVerify(exactly = 0) { mockRepoSearch.searchImages(QUERY) }
        coVerify(exactly = 1) { mockRepoCache.searchImages(QUERY) }
    }

    @Test
    fun `invoke when ERRORs`() = runTest {
        coEvery { mockRepoCache.searchImages(QUERY) } returns Status.ERROR(EXCEPTION)
        coEvery { mockRepoSearch.searchImages(QUERY) } returns Status.ERROR(EXCEPTION)
        val tested = SearchImagesUseCase(mockRepoSearch, mockRepoCache)

        val result: Result<SearchImagesResponse> = tested.invoke(QUERY)

        assert(result is Status.ERROR)
        val success = result as Status.ERROR
        Assert.assertEquals(EXCEPTION, success.throwable)
        coVerify(exactly = 1) { mockRepoSearch.searchImages(QUERY) }
        coVerify(exactly = 1) { mockRepoCache.searchImages(QUERY) }
    }

    private companion object {
        const val IMAGE_ID = 123L
        const val QUERY = "query"
        val EXCEPTION = Exception()
    }
}