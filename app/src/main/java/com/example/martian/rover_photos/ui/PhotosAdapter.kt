package com.example.martian.rover_photos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.martian.databinding.RoverPhotoItemBinding
import com.example.martian.rover_photos.data.PhotoModel
import com.squareup.picasso.Picasso
import android.R
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedListAdapter


class PhotosAdapter : PagedListAdapter<PhotoModel, PhotosAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position)
        photo?.let {
            holder.apply {
                bind(createOnClickListener(photo.id), photo)
                itemView.tag = photo
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RoverPhotoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun createOnClickListener(id: Int): View.OnClickListener {
        return View.OnClickListener {
            val direction =
                RoverPhotosFragmentDirections.actionRoverPhotosToPhotoFragment(
                    id
                )
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder (
        private val binding: RoverPhotoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: PhotoModel) {
            binding.apply {
                clickListener = listener
                photo = item
                executePendingBindings()
                val picasso = Picasso.get()
                picasso.load(item.imgSrc.replace("http", "https")).into(image)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<PhotoModel>() {

    override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
        return oldItem == newItem
    }
}