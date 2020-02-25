package com.wilburt.fileloader.photos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wilburt.fileloader.photos.datasource.RemoteDataSource
import com.wilburt.fileloader.photos.repositories.PhotoRepository
import com.wilburt.fileloader.photos.models.PhotosResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoViewModel : ViewModel() {

    private val repository = PhotoRepository(RemoteDataSource())
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
