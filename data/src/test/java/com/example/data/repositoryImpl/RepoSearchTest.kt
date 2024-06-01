package com.example.data.repositoryImpl

import com.example.data.api.ApiPixBay
import com.example.domain.model.Hit
import com.example.domain.model.SearchImagesResponse
import com.example.domain.model.Status
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RepoSearchTest {

    private val mockApi: ApiPixBay = mockk(relaxed = true)
    private val mockValidResponseSearchImages: Response<com.example.data.model.SearchImagesResponse> =
        mockk(relaxed = true) {
            every { isSuccessful } returns true
            every { body() } returns SEARCH_IMAGES_RESPONSE_DATA
        }

    private val mockValidResponseSearchImage: Response<com.example.data.model.SearchImagesResponse> =
        mockk(relaxed = true) {
            every { isSuccessful } returns true
            every { body() } returns SEARCH_IMAGE_RESPONSE_DATA
        }

    @Before
    fun setUp() {
    }

    @Test
    fun `searchImages() when fail`() = runTest {
        val tested = RepoSearch(mockApi)

        val actual = tested.searchImages(QUERY)
        assert(actual is Status.ERROR)
    }

    @Test
    fun `searchImages()`() = runTest {
        coEvery { mockApi.searchImages(QUERY) } returns mockValidResponseSearchImages
        val tested = RepoSearch(mockApi)

        val actual = tested.searchImages(QUERY)
        assert(actual is Status.SUCCESS)
        val actualSuccess = actual as Status.SUCCESS

        Assert.assertEquals(HITS_DOMAIN.size, actualSuccess.value.hits.size)
        actualSuccess.value.hits.forEach { expected ->
            assert(actualSuccess.value.hits.contains(expected))
        }

    }

    @Test
    fun `searchImage() when fail`() = runTest {
        val tested = RepoSearch(mockApi)

        val actual = tested.searchImage(123)
        assert(actual is Status.ERROR)
    }

    @Test
    fun `searchImage()`() = runTest {
        val expectedId = HITS_DATA[0].id
        coEvery { mockApi.searchImage(expectedId) } returns mockValidResponseSearchImage

        val tested = RepoSearch(mockApi)

        val actual = tested.searchImage(HITS_DATA[0].id)
        assert(actual is Status.SUCCESS)
        val actualSuccess = actual as Status.SUCCESS

        Assert.assertEquals(HITS_DOMAIN[0], actualSuccess.value)
    }

    private companion object {
        const val QUERY = "QUERY"
        val HITS_DOMAIN = (0..10).map {
            Hit(
                id = it.toLong(),
                previewUrl = "previewUrl_$it",
                largeImageUrl = "largeImageUrl_$it",
                user = "user_$it",
                likes = it * 10,
                comments = it * 10,
                downloads = it * 100,
                tags = "tags_$it"
            )
        }

        val SEARCH_IMAGE_RESPONSE_DOMAIN =
            SearchImagesResponse(HITS_DOMAIN.size, HITS_DOMAIN.size, HITS_DOMAIN)

        val HITS_DATA = HITS_DOMAIN.map {
            com.example.data.model.Hit(
                id = it.id,
                previewUrl = it.previewUrl,
                largeImageUrl = it.largeImageUrl,
                user = it.user,
                likes = it.likes,
                comments = it.comments,
                downloads = it.downloads,
                tags = it.tags,
            )
        }

        val SEARCH_IMAGES_RESPONSE_DATA =
            com.example.data.model.SearchImagesResponse(HITS_DATA.size, HITS_DATA.size, HITS_DATA)

        val SEARCH_IMAGE_RESPONSE_DATA =
            com.example.data.model.SearchImagesResponse(1, 1, listOf(HITS_DATA[0]))

    }
}