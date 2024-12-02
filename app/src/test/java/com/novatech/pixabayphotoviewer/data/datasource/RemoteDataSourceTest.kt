package com.novatech.pixabayphotoviewer.data.datasource

import com.google.common.truth.Truth.assertThat
import com.novatech.pixabayphotoviewer.data.remote.PixabayAPI
import com.novatech.pixabayphotoviewer.data.remote.dto.ImageHit
import com.novatech.pixabayphotoviewer.data.remote.dto.PixabayResponse

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Test class for verifying the functionality of [RemoteDataSource].
 */
@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private val pixabayApi: PixabayAPI = mockk()

    @Before
    fun setUp() {
        remoteDataSource = RemoteDataSource(pixabayApi)
    }

    @Test
    fun `fetchImages returns success when API responds with data`() = runTest {
        val page = 1
        val mockImageHits = listOf(
            ImageHit(
                id = 1,
                previewURL = "https://pixabay.com/preview1.jpg",
                largeImageURL = "https://pixabay.com/fullsize1.jpg",
                user = "User1",
                imageSize = 1024,
                type = "photo",
                tags = "nature, forest",
                views = 100,
                likes = 50,
                comments = 10,
                favorites = 5,
                downloads = 20
            )
        )
        val mockResponse = PixabayResponse(hits = mockImageHits, total = 1, totalHits = 1)

        coEvery { pixabayApi.fetchImages(any(), any(), any(), any()) } returns mockResponse

        val result = remoteDataSource.fetchImages(page)

        assertThat(result.isSuccess).isTrue()
        val images = result.getOrNull()
        assertThat(images).isNotNull()
        assertThat(images!!.size).isEqualTo(1)
        assertThat(images[0].id).isEqualTo(1)
    }

    @Test
    fun `fetchImages returns failure when API throws an exception`() = runTest {
        val page = 1
        val errorMessage = "Network error"
        coEvery { pixabayApi.fetchImages(any(), any(), any(), any()) } throws Exception(errorMessage)

        val result = remoteDataSource.fetchImages(page)

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo(errorMessage)
    }

    @Test
    fun `fetchImages returns success with empty list when API responds with no data`() = runTest {
        val page = 1
        val mockResponse = PixabayResponse(hits = emptyList(), total = 0, totalHits = 0)

        coEvery { pixabayApi.fetchImages(any(), any(), any(), any()) } returns mockResponse

        val result = remoteDataSource.fetchImages(page)

        assertThat(result.isSuccess).isTrue()
        val images = result.getOrNull()
        assertThat(images).isNotNull()
        assertThat(images!!.isEmpty()).isTrue()
    }
}
