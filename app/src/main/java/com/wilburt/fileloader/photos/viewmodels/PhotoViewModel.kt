package com.wilburt.fileloader.photos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wilburt.fileloader.di.module.viewmodel.CoroutineContextProvider
import com.wilburt.fileloader.photos.models.PhotosResponse
import com.wilburt.fileloader.photos.models.Status
import com.wilburt.fileloader.photos.repositories.PhotoRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoViewModel @Inject constructor(
    private val contextProvider: CoroutineContextProvider,
    private val repository: PhotoRepository
) : ViewModel() {
    private val _photosResponse = MutableLiveData<PhotosResponse>()
    val photosResponse: LiveData<PhotosResponse> = _photosResponse

    fun fetchPhotos() {
        viewModelScope.launch {

            // Notify observers that data load has started
            _photosResponse.value = PhotosResponse(Status.Loading)

            val response = withContext(contextProvider.IO) { repository.fetchPhotos() }

            // Notify observers that data load has completed
            _photosResponse.value = response
        }
    }

}
