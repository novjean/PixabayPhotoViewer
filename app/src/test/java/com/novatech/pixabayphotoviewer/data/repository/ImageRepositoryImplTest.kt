package com.novatech.pixabayphotoviewer.data.repository

import com.google.common.truth.Truth.assertThat
import com.novatech.pixabayphotoviewer.data.datasource.RemoteDataSource
import com.novatech.pixabayphotoviewer.domain.model.Image
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ImageRepositoryImplTest {

    private lateinit var repository: ImageRepositoryImpl
    private val remoteDataSource: RemoteDataSource = mockk()

    @Before
    fun setUp() {
        repository = ImageRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `fetchImages returns success when remoteDataSource returns a valid list`() = runTest {
        // Arrange
        val mockImages = listOf(
            Image(
                id = 1,
                previewURL = "https://example.com/preview1.jpg",
                fullSizeURL = "https://example.com/fullsize1.jpg",
                userName = "User1",
                imageSize = 1024,
                imageType = "photo",
                tags = listOf("nature", "forest"),
                views = 100,
                likes = 50,
                comments = 10,
                favorites = 5,
                downloads = 20
            )
        )
        coEvery { remoteDataSource.fetchImages(page = 1) } returns Result.success(mockImages)

        // Act
        val result = repository.fetchImages(page = 1)

        // Assert
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(mockImages)
    }

    @Test
    fun `fetchImages returns failure when remoteDataSource throws an exception`() = runTest {
        // Arrange
        val exception = Exception("Network error")
        coEvery { remoteDataSource.fetchImages(page = 1) } returns Result.failure(exception)

        // Act
        val result = repository.fetchImages(page = 1)

        // Assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(exception)
    }

    @Test
    fun `fetchImages delegates call to remoteDataSource with correct parameters`() = runTest {
        // Arrange
        val page = 2
        coEvery { remoteDataSource.fetchImages(page = page) } returns Result.success(emptyList())

        // Act
        repository.fetchImages(page = page)

        // Assert
        coVerify(exactly = 1) { remoteDataSource.fetchImages(page = page) }
    }
}