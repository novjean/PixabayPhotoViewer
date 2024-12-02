package com.novatech.pixabayphotoviewer.domain.use_case.fetch_images

import com.novatech.pixabayphotoviewer.domain.model.Image
import com.novatech.pixabayphotoviewer.domain.repository.ImageRepository

/**
 * Use case for fetching images from the repository.
 *
 * @param page The page number for paginated results.
 * @return A [Result] containing a list of [Image] objects or an error.
 */
class FetchImagesUseCase(private val repository: ImageRepository) {
    suspend operator fun invoke(page: Int): Result<List<Image>> {
        return repository.fetchImages(page)
    }
}