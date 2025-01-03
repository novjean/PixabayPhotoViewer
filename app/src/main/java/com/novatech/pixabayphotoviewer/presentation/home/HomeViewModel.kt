package com.novatech.pixabayphotoviewer.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novatech.pixabayphotoviewer.domain.model.Image
import com.novatech.pixabayphotoviewer.domain.use_case.fetch_images.FetchImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Home screen.
 * Fetches and manages a list of images for the home screen.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchImagesUseCase: FetchImagesUseCase
) : ViewModel() {
    private val _images = MutableLiveData<List<Image>>()
    val images: LiveData<List<Image>> get() = _images

    private var currentPage = 1

    /**
     * Fetches a paginated list of images.
     * Updates the UI state with the fetched data or an error message.
     */
    fun loadImages() {
        viewModelScope.launch {
            val result = fetchImagesUseCase(currentPage)
            result.onSuccess { imageList ->
                val updatedList = _images.value.orEmpty() + imageList
                _images.value = updatedList
                currentPage++
            }.onFailure {
                // Handle error
            }
        }
    }
}