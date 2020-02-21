package com.wilburt.fileloader.photos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wilburt.fileloader.photos.repositories.PhotoRepository
import com.wilburt.fileloader.photos.models.PhotosResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoViewModel : ViewModel() {

    private val repository = PhotoRepository()
    val photosResponse: LiveData<PhotosResponse>

    init {
        photosResponse = repository.photosResponse;
    }

    fun fetchPhotos() {
        viewModelScope.launch {
            repository.fetchPhotos()
        }
    }

}
