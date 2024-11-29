package com.novatech.pixabayphotoviewer.domain.use_case.fetch_images

import com.google.common.truth.Truth.assertThat
import com.novatech.pixabayphotoviewer.domain.model.Image
import com.novatech.pixabayphotoviewer.domain.repository.ImageRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchImagesUseCaseTest {

    private lateinit var fetchImagesUseCase: FetchImagesUseCase
    private val repository: ImageRepository = mockk()

    @Before
    fun setUp() {
        fetchImagesUseCase = FetchImagesUseCase(repository)
    }

    @Test
    fun `fetchImages succeeds when repository returns a list of images`() = runTest {
        // Arrange
        val page = 1
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
        coEvery { repository.fetchImages(page) } returns Result.success(mockImages)

        // Act
        val result = fetchImagesUseCase(page)

        // Assert
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(mockImages)
        coVerify(exactly = 1) { repository.fetchImages(page) }
    }

    @Test
    fun `fetchImages fails when repository returns an error`() = runTest {
        // Arrange
        val page = 2
        val errorMessage = "Network error"
        coEvery { repository.fetchImages(page) } returns Result.failure(Exception(errorMessage))

        // Act
        val result = fetchImagesUseCase(page)

        // Assert
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).isEqualTo(errorMessage)
        coVerify(exactly = 1) { repository.fetchImages(page) }
    }
}