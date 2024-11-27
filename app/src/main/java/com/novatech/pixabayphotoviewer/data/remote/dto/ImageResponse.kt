package com.novatech.pixabayphotoviewer.data.remote.dto

data class ImageResponse(
    val hits: List<ImageResult>,
    val total: Int,
    val totalHits: Int
)
