package com.wilburt.fileloader.photos.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.wilburt.fileloader.photos.models.Photo
import com.wilburt.fileloader.photos.models.PhotosResponse
import com.wilburt.fileloader.photos.models.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import timber.log.Timber
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by Wilberforce on 2020-02-21 at 17:16.
 */
class PhotoRepository {

    val photosResponse = MutableLiveData<PhotosResponse>()


    suspend fun fetchPhotos() {
        withContext(Dispatchers.IO) {
            // Notify observers that data load has started
            photosResponse.postValue(PhotosResponse(Status.loading))
            try {

                // Fetch the data
                val connection = URL(imagesUrl).openConnection() as HttpURLConnection
                try {
                    connection.inputStream.bufferedReader().use {

                        // Deserialize the response to a list of Photos
                        val photos = parseData(it.readText())

                        // Notify observers that the data load was successful
                        photosResponse.postValue(PhotosResponse(Status.success, photos))
                    }
                } finally {
                    connection.disconnect()
                }
            } catch (e: Exception) {
                Timber.e(e)
                // Notify observers that the data load was unsuccessful
                photosResponse.postValue(PhotosResponse(Status.error))
            }
        }
    }

    @WorkerThread
    private fun parseData(json: String): List<Photo> {
        val jsonArray = JSONArray(json)

        val photos = mutableListOf<Photo>()

        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)

            // id
            val id = item.getString("id")

            // Photographer
            val user = item.getJSONObject("user")
            val name = user.getString("name")
            val avatarUrl = user.getJSONObject("profile_image").getString("medium")

            val imageUrl = item.getJSONObject("urls").getString("small")

            val photo = Photo(id, name, avatarUrl, imageUrl)
            photos.add(photo)
        }
        return photos
    }
}

private const val imagesUrl = "https://pastebin.com/raw/wgkJgazE"