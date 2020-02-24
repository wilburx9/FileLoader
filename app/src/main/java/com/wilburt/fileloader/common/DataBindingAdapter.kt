package com.wilburt.fileloader.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.wilburt.fileloader.R
import com.wilburt.fileloader.image.CircularTransformation
import com.wilburt.fileloader.image.ImageLoader
import com.wilburt.fileloader.photos.models.Photo


/**
 * Created by Wilberforce on 2020-02-22 at 03:04.
 */
object DataBindingAdapter {
    @BindingAdapter("android:src")
    @JvmStatic
    fun setPhotoImage(view: ImageView, model: Photo?) {
        ImageLoader.with(view.context).load(
            view,
            model?.imageUrl,
            R.drawable.ic_image
        )
    }

    @BindingAdapter("android:avatar")
    @JvmStatic
    fun setAvatar(view: ImageView, model: Photo?) {
        ImageLoader.with(view.context).load(
            view,
            model?.photographerAvatarUrl,
            R.drawable.ic_person,
            listOf(CircularTransformation())
        )
    }
}