package com.novatech.pixabayphotoviewer.domain.use_case.fetch_images

import com.novatech.pixabayphotoviewer.domain.model.Image
import com.novatech.pixabayphotoviewer.domain.repository.ImageRepository

class FetchImagesUseCase(private val repository: ImageRepository) {
    suspend operator fun invoke(page: Int): Result<List<Image>> {
        return repository.fetchImages(page)
    }
}