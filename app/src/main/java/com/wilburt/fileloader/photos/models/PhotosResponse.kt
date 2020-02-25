package com.wilburt.fileloader.photos.models


/**
 * Created by Wilberforce on 2020-02-21 at 17:17.
 */
data class PhotosResponse(val status: Status, val photos: List<Photo> = emptyList())

enum class Status { Loading, Success, Error }