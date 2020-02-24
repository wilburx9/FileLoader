package com.wilburt.fileloader.image

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.wilburt.fileloader.FileLoader
import java.io.BufferedInputStream
import java.util.*
import java.util.Collections.synchronizedMap


/**
 * Created by Wilberforce on 2020-02-24 at 16:26.
 */
class ImageLoader(context: Context) : FileLoader<Bitmap, ImageRequest>() {

    private val imageViewMap = synchronizedMap(WeakHashMap<ImageView, String>())


    init {
        val metrics = context.resources.displayMetrics
        screenWidth = metrics.widthPixels
        screenHeight = metrics.heightPixels
    }

    companion object {
        private var INSTANCE: ImageLoader? = null
        internal var screenWidth = 0
        internal var screenHeight = 0

        @Synchronized
        fun with(context: Context?): ImageLoader {
            require(context != null) {
                "ImageLoader:with - Context should not be null."
            }

            return INSTANCE ?: ImageLoader(context).also {
                INSTANCE = it
            }
        }
    }

    fun load(
        imageView: ImageView?, imageUrl: String?, @DrawableRes placeHolder: Int, transformations:
        List<Transformations>? = null
    ) {
        require(imageView != null) {
            "ImageLoader:load - ImageView should not be null."
        }

        imageView.setImageResource(placeHolder)

        if (imageUrl.isNullOrBlank()) return

        imageViewMap[imageView] = imageUrl

        val bitmap = checkFileInCache(imageUrl)

        bitmap?.let {
            loadImageIntoImageView(imageView, it, imageUrl, transformations)
        } ?: run {
            submitRequest(ImageRequest(imageUrl, imageView, transformations))
        }

    }

    private fun loadImageIntoImageView(
        imageView: ImageView, bitmap: Bitmap?, imageUrl: String, trans: List<Transformations>?
    ) {
        require(bitmap != null) {
            "ImageLoader:loadImageIntoImageView - Bitmap should not be null"
        }
        val scaledBitmap = Utils.scaleBitmapForLoad(bitmap, imageView.width, imageView.height)

        scaledBitmap?.let {
            if (!isImageViewReused(ImageRequest(imageUrl, imageView, trans))) {
                var transformedBitmap = it
                trans?.forEach { trans ->
                    transformedBitmap = trans.transform(transformedBitmap)
                }
                imageView.setImageBitmap(transformedBitmap)
            }
        }
    }

    override fun inputStreamToType(inputStream: BufferedInputStream): Bitmap? {
        return Utils.scaleBitmap(
            inputStream,
            screenWidth,
            screenHeight
        );
    }

     override fun consumeFile(request: ImageRequest) {
        if (!isImageViewReused(request)) {
            loadImageIntoImageView(
                request.key as ImageView, checkFileInCache(request.url), request.url,
                request.transformations
            )
        }
    }

    override fun cacheSizeOf(key: String, value: Bitmap): Int {
        // The cache size will be measured in kilobytes rather than number of items
        return value.byteCount / 1024
    }

    private fun isImageViewReused(request: ImageRequest): Boolean {
        val tag = imageViewMap[request.key]
        return tag == null || tag != request.url
    }

    override fun shouldDownload(request: ImageRequest) = isImageViewReused(request)

}
