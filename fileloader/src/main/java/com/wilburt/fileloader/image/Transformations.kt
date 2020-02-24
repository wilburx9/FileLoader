package com.wilburt.fileloader.image

import android.R.attr
import android.graphics.*
import kotlin.math.min


/**
 * Created by Wilberforce on 2020-02-22 at 03:18.
 */
interface Transformations {
    fun transform(bitmap: Bitmap): Bitmap
}

class CircularTransformation : Transformations {
    override fun transform(bitmap: Bitmap): Bitmap {
        val size = min(bitmap.height, bitmap.width)
        val radius = size / 2F
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

        val color = 0xff424242.toInt()
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawCircle(radius, radius, radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }
}