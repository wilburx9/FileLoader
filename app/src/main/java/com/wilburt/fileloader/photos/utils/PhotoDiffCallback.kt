package com.wilburt.fileloader.photos.utils

import androidx.recyclerview.widget.DiffUtil
import com.wilburt.fileloader.photos.models.Photo


/**
 * Created by Wilberforce on 2020-02-21 at 16:58.
 */

class PhotoDiffCallback(private val oldList: List<Photo>, private val newList: List<Photo>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}
