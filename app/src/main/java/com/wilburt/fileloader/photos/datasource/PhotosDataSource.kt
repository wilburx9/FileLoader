package com.wilburt.fileloader.photos.datasource

import androidx.annotation.WorkerThread


/**
 * Created by Wilberforce on 2020-02-25 at 01:00.
 */
interface PhotosDataSource {

    @WorkerThread
    fun getJson(): String
}