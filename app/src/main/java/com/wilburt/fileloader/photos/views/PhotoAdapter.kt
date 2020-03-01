package com.wilburt.fileloader.photos.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wilburt.fileloader.databinding.ItemPhotoBinding
import com.wilburt.fileloader.photos.utils.PhotoDiffCallback
import com.wilburt.fileloader.photos.models.Photo


/**
 * Created by Wilberforce on 2020-02-21 at 16:56.
 */
class PhotoAdapter(private var items: List<Photo> = emptyList()) : RecyclerView.Adapter<PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemPhotoBinding.inflate(inflater, parent, false)
        return PhotosViewHolder(itemBinding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) = holder.bind(items[position])

    fun updateItems(items: List<Photo>) {
        val result = DiffUtil.calculateDiff(
            PhotoDiffCallback(
                this.items,
                items
            )
        )
        result.dispatchUpdatesTo(this)
        this.items = items

    }
}

class PhotosViewHolder(private var binding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(model: Photo) {
        binding.model = model
        binding.executePendingBindings()
    }


}