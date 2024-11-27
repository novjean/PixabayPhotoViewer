package com.novatech.pixabayphotoviewer.data.repository

import com.novatech.pixabayphotoviewer.data.datasource.RemoteDataSource
import com.novatech.pixabayphotoviewer.domain.model.Image
import com.novatech.pixabayphotoviewer.domain.repository.ImageRepository

class ImageRepositoryImpl(private val remoteDataSource: RemoteDataSource) : ImageRepository {
    override suspend fun fetchImages(page: Int): Result<List<Image>> {
        return remoteDataSource.fetchImages(page)
    }
}