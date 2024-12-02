package com.novatech.pixabayphotoviewer.data.datasource

import com.novatech.pixabayphotoviewer.BuildConfig
import com.novatech.pixabayphotoviewer.data.remote.PixabayAPI
import com.novatech.pixabayphotoviewer.domain.model.Image

/**
 * Data source for fetching images from the Pixabay API.
 * Handles API calls and maps the response into domain models.
 */
class RemoteDataSource(private val pixabayApi: PixabayAPI) {

    /**
     * Fetches a list of images from the Pixabay API.
     *
     * @param page The page number for paginated results.
     * @return A [Result] containing a list of [Image] objects or an error.
     */
    suspend fun fetchImages(page: Int): Result<List<Image>> {
        return try {
            val response = pixabayApi.fetchImages(
                apiKey = BuildConfig.API_KEY,
                "nature",
                page = page,
                perPage = 20)
            Result.success(response.hits.map { hit ->
                Image(
                    id = hit.id,
                    previewURL = hit.previewURL,
                    fullSizeURL = hit.largeImageURL,
                    userName = hit.user,
                    imageSize = hit.imageSize,
                    imageType = hit.type,
                    tags = hit.tags.split(", "),
                    views = hit.views,
                    likes = hit.likes,
                    comments = hit.comments,
                    favorites = hit.favorites,
                    downloads = hit.downloads
                )
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
