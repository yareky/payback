package com.example.domain.usecase

import com.example.domain.model.Hit
import com.example.domain.model.Result
import com.example.domain.model.Status
import com.example.domain.repository.IRepoCache
import com.example.domain.repository.IRepoSearch
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


class GetImagesUseCaseTest {
    private val mockRepoCache: IRepoCache = mockk(relaxed = true)
    private val mockRepoSearch: IRepoSearch = mockk(relaxed = true)
    private val mockHit: Hit = mockk(relaxed = true)

    @Test
    fun `invoke when no cached image`() = runTest {
        coEvery { mockRepoCache.searchImage(any()) } returns Status.ERROR(EXCEPTION)
        coEvery { mockRepoSearch.searchImage(any()) } returns Status.SUCCESS(mockHit)
        val tested = GetImagesUseCase(mockRepoSearch, mockRepoCache)

        val result: Result<Hit> = tested.invoke(IMAGE_ID)

        assert(result is Status.SUCCESS)
        val success = result as Status.SUCCESS
        Assert.assertEquals(mockHit, success.value)
        coVerify(exactly = 1) { mockRepoSearch.searchImage(IMAGE_ID) }
        coVerify(exactly = 1) { mockRepoCache.searchImage(IMAGE_ID) }
    }

    @Test
    fun `invoke when cached image`() = runTest {
        coEvery { mockRepoCache.searchImage(any()) } returns Status.SUCCESS(mockHit)
        coEvery { mockRepoSearch.searchImage(any()) } returns Status.ERROR(EXCEPTION)
        val tested = GetImagesUseCase(mockRepoSearch, mockRepoCache)

        val result: Result<Hit> = tested.invoke(IMAGE_ID)

        assert(result is Status.SUCCESS)
        val success = result as Status.SUCCESS
        Assert.assertEquals(mockHit, success.value)
        coVerify(exactly = 0) { mockRepoSearch.searchImage(IMAGE_ID) }
        coVerify(exactly = 1) { mockRepoCache.searchImage(IMAGE_ID) }
    }

    private companion object {
        const val IMAGE_ID = 123L
        val EXCEPTION = Exception()
    }
}