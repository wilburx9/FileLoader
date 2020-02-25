package com.wilburt.fileloader.photos.datasource

import androidx.annotation.WorkerThread
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by Wilberforce on 2020-02-25 at 01:01.
 */

class RemoteDataSource : PhotosDataSource {

    @WorkerThread
    override fun getJson(): String {
        // Fetch the data
        val connection = URL(imagesUrl).openConnection() as HttpURLConnection
        try {
            connection.inputStream.bufferedReader().use {
                // Return the raw response string
                return it.readText();
            }
        } finally {
            connection.disconnect()
        }
    }

}

private const val imagesUrl = "https://pastebin.com/raw/wgkJgazE"