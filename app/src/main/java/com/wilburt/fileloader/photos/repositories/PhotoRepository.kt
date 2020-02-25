package com.wilburt.fileloader.photos.repositories

import androidx.annotation.WorkerThread
import com.wilburt.fileloader.photos.datasource.PhotosDataSource
import com.wilburt.fileloader.photos.models.Photo
import com.wilburt.fileloader.photos.models.PhotosResponse
import com.wilburt.fileloader.photos.models.Status
import org.json.JSONArray
import timber.log.Timber


/**
 * Created by Wilberforce on 2020-02-21 at 17:16.
 */
class PhotoRepository(private val dataSource: PhotosDataSource) {



    @WorkerThread
     fun fetchPhotos(): PhotosResponse {
        return try {

            // Fetch the data
            val jsonResponse = dataSource.getJson()

            // Deserialize the response to a list of Photos
            val photos = parseData(jsonResponse)

            // Notify observers that the data load was successful
            PhotosResponse(Status.Success, photos)
        } catch (e: Exception) {
            Timber.e(e)
            // Notify observers that the data load was unsuccessful
            PhotosResponse(Status.Error)
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
