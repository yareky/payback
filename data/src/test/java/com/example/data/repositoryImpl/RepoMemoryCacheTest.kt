package com.example.data.repositoryImpl

import com.example.domain.model.Hit
import com.example.domain.model.Result
import com.example.domain.model.SearchImagesResponse
import com.example.domain.model.Status
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class RepoMemoryCacheTest {
    private lateinit var hitsMap: MutableMap<Long, Hit>
    private lateinit var queryMap: MutableMap<String, List<Long>>

    @Before
    fun tearUp() {
        hitsMap = mutableMapOf()
        queryMap = mutableMapOf()
    }

    @Test
    fun putImages() = runTest {

        val tested = RepoMemoryCache(hitsMap, queryMap)

        tested.putImages(HITS)

        Assert.assertEquals(0, queryMap.entries.size)
        Assert.assertEquals(HITS.size, hitsMap.entries.size)

        HITS.forEach { expected ->
            val hit = hitsMap[expected.id]
            Assert.assertNotNull(hit)
            Assert.assertEquals(expected, hit)
        }
    }

    @Test
    fun `putImages empty list`() = runTest {
        val tested = RepoMemoryCache(hitsMap, queryMap)

        tested.putImages(emptyList())

        Assert.assertEquals(0, queryMap.entries.size)
        Assert.assertEquals(0, hitsMap.entries.size)
    }

    @Test
    fun cacheQuery() = runTest {
        val tested = RepoMemoryCache(hitsMap, queryMap)

        tested.cacheQuery(QUERY, SEARCH_IMAGE_RESPONSE)

        Assert.assertEquals(1, queryMap.entries.size)
        Assert.assertEquals(HITS.size, hitsMap.entries.size)

        val ids = queryMap[QUERY]
        Assert.assertNotNull(ids)
        Assert.assertEquals(HITS.size, ids?.size)

        HITS.forEach { expected ->
            val hit = hitsMap[expected.id]
            Assert.assertNotNull(hit)
            Assert.assertEquals(expected, hit)

            assert(ids!!.contains(expected.id))
        }
    }

    @Test
    fun searchImages() = runTest {
        hitsMap.putAll(HITS.associateBy { it.id })
        queryMap[QUERY] = HITS.map { it.id }
        val tested = RepoMemoryCache(hitsMap, queryMap)

        val actual: Result<SearchImagesResponse> = tested.searchImages(QUERY)

        assert(actual is Status.SUCCESS)
        val actualSuccess = actual as Status.SUCCESS

        Assert.assertEquals(HITS.size, actualSuccess.value.hits.size)

        HITS.forEach { expected ->
            assert(actualSuccess.value.hits.contains(expected))
        }

        Assert.assertEquals(1, queryMap.entries.size)
        Assert.assertEquals(HITS.size, hitsMap.entries.size)
    }

    @Test
    fun `searchImages() when empty`() = runTest {
        val tested = RepoMemoryCache(hitsMap, queryMap)

        val actual: Result<SearchImagesResponse> = tested.searchImages(QUERY)

        assert(actual is Status.ERROR)

        Assert.assertEquals(0, queryMap.entries.size)
        Assert.assertEquals(0, hitsMap.entries.size)
    }

    @Test
    fun searchImage() = runTest {
        hitsMap.putAll(HITS.associateBy { it.id })
        queryMap[QUERY] = HITS.map { it.id }
        val tested = RepoMemoryCache(hitsMap, queryMap)

        HITS.forEach { expected ->
            val actual: Result<Hit> = tested.searchImage(expected.id)
            assert(actual is Status.SUCCESS)
            val actualSuccess = actual as Status.SUCCESS

            Assert.assertEquals(expected, actualSuccess.value)
        }

        Assert.assertEquals(1, queryMap.entries.size)
        Assert.assertEquals(HITS.size, hitsMap.entries.size)
    }

    private companion object {
        const val QUERY = "QUERY"
        val HITS = (0..10).map {
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

        val SEARCH_IMAGE_RESPONSE = SearchImagesResponse(HITS.size, HITS.size, HITS)
    }
}