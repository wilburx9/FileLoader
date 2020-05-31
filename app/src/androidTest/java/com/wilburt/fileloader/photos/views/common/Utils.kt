package com.wilburt.fileloader.photos.views.common

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.wilburt.fileloader.photos.views.PhotosViewHolder
import org.hamcrest.Description
import org.hamcrest.Matcher
import java.lang.IllegalArgumentException


/**
 * Created by Wilberforce on 29/02/2020 at 19:17.
 */
object Utils {
    fun atPosition(position: Int, text: String): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                //description.appendText(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder  = view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
                if (viewHolder is PhotosViewHolder){
                    return viewHolder.getTitle().equals(text)
                }
                throw IllegalArgumentException("$viewHolder is not supported")
            }
        }
    }
}