package com.novatech.pixabayphotoviewer.data.remote.dto

data class PixabayResponse(
    val hits: List<ImageHit>,
    val total: Int,
    val totalHits: Int
)
