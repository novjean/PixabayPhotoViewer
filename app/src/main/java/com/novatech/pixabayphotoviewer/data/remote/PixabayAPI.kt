package com.novatech.pixabayphotoviewer.data.remote

import com.novatech.pixabayphotoviewer.BuildConfig
import com.novatech.pixabayphotoviewer.data.remote.dto.PixabayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun fetchImages(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20
    ): PixabayResponse
}