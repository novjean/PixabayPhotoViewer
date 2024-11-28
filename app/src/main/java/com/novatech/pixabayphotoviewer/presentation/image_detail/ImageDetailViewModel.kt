package com.novatech.pixabayphotoviewer.presentation.image_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.novatech.pixabayphotoviewer.domain.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor() : ViewModel() {

    private val _image = MutableLiveData<Image>()
    val image: LiveData<Image> get() = _image

    val tags = MutableLiveData<String>()
    val size = MutableLiveData<String>()
    val type = MutableLiveData<String>()
    val uploader = MutableLiveData<String>()
    val views = MutableLiveData<String>()
    val likes = MutableLiveData<String>()
    val comments = MutableLiveData<String>()
    val favorites = MutableLiveData<String>()
    val downloads = MutableLiveData<String>()

    fun setImageDetail(imageDetail: Image) {
        _image.value = imageDetail

        tags.value = "Tags: ${imageDetail.tags.joinToString(", ")}"
        size.value = "Size: ${formatSize(imageDetail.imageSize)}"
        type.value = "Type: ${imageDetail.imageType}"
        uploader.value = "Uploaded by: ${imageDetail.userName}"
        views.value = "Views: ${imageDetail.views}"
        likes.value = "Likes: ${imageDetail.likes}"
        comments.value = "Comments: ${imageDetail.comments}"
        favorites.value = "Favorites: ${imageDetail.favorites}"
        downloads.value = "Downloads: ${imageDetail.downloads}"
    }

    private fun formatSize(sizeInBytes: Int): String {
        val sizeInMB = sizeInBytes / (1024 * 1024.0)
        return String.format("%.2f MB", sizeInMB)
    }
}
