package com.novatech.pixabayphotoviewer.data.remote.dto

data class ImageHit(
    val id: Int,
    val previewURL: String,   // URL for thumbnail
    val largeImageURL: String,// URL for full-size image
    val user: String,         // Uploader's username
    val imageSize: Int,       // Image size in bytes
    val type: String,         // Image type (e.g., photo)
    val tags: String,         // Comma-separated tags
    val views: Int,
    val likes: Int,
    val comments: Int,
    val favorites: Int,
    val downloads: Int
)
