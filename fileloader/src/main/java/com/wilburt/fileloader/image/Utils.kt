package com.wilburt.fileloader.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


/**
 * Created by Wilberforce on 2020-02-24 at 16:36.
 */
object Utils {
    internal fun scaleBitmapForLoad(bitmap: Bitmap, width: Int, height: Int): Bitmap? {
        if (width == 0 || height == 0) return  bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val inputStream = BufferedInputStream(ByteArrayInputStream(stream.toByteArray()))

        // Scale Bitmap to required ImageView Size
        return scaleBitmap(inputStream, width, height)
    }



    internal fun scaleBitmap(inputStream: BufferedInputStream, width: Int, height: Int): Bitmap? {
        return BitmapFactory.Options().run {
            inputStream.mark(inputStream.available())

            inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, this)
            inSampleSize = calculateInSampleSize(this, width, height)
            inJustDecodeBounds = false
            inputStream.reset()
            BitmapFactory.decodeStream(inputStream, null, this)
        }
    }


    // Source: https://developer.android.com/topic/performance/graphics/load-bitmap#load-bitmap
    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of a 2 and keeps both
            // height and width larger than the requested height and width
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return  inSampleSize
    }
}