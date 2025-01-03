package com.novatech.pixabayphotoviewer.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val id: Int,
    val previewURL: String, // Thumbnail URL
    val fullSizeURL: String,
    val userName: String,
    val imageSize: Int,
    val imageType: String,
    val tags: List<String>,
    val views: Int,
    val likes: Int,
    val comments: Int,
    val favorites: Int,
    val downloads: Int
) : Parcelable