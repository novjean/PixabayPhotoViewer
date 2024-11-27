package com.novatech.pixabayphotoviewer.data.remote

import com.novatech.pixabayphotoviewer.BuildConfig
import com.novatech.pixabayphotoviewer.data.remote.dto.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}