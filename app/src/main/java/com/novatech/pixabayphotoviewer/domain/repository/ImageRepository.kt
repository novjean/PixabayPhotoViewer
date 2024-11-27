package com.novatech.pixabayphotoviewer.domain.repository

import com.novatech.pixabayphotoviewer.domain.model.Image

interface ImageRepository {
    suspend fun fetchImages(page: Int): Result<List<Image>>
}