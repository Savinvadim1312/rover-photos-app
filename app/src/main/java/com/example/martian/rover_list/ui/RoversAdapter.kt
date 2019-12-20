package com.example.martian.rover_photos.ui

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.martian.databinding.RoverBinding
import com.squareup.picasso.Picasso
import com.example.martian.rover_list.data.RoverModel
import com.example.martian.rover_list.ui.RoversListFragmentDirections
import android.util.Log


class RoversAdapter(val width: Int) : ListAdapter<RoverModel, RoversAdapter.ViewHolder>(
    DiffCallbackRover()
) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var rover: RoverModel = getItem(position)
        holder.apply {
            bind(createOnClickListener(rover.name), rover, width)
            itemView.tag = rover
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RoverBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun createOnClickListener(name: String): View.OnClickListener {
        return View.OnClickListener {
            val direction =
                RoversListFragmentDirections.actionRoversListToRoverPhotos(
                    name
                )
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder (
        private val binding: RoverBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: RoverModel, width: Int) {
            binding.apply {
                clickListener = listener
                rover = item

                root.layoutParams = ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT)

                executePendingBindings()
            }

            var image = "https://www.sciencemag.org/sites/default/files/styles/article_main_large/public/sn-curiosity.jpg?itok=ok6vgedp"
            if(item.id == 5) {
                image = "https://www.sciencemag.org/sites/default/files/styles/article_main_large/public/sn-curiosity.jpg?itok=ok6vgedp"
            } else if(item.id == 6) {
                image = "https://www.sciencemag.org/sites/default/files/styles/article_main_large/public/sn-curiosity.jpg?itok=ok6vgedp"
            }
            val picasso = Picasso.get()
            picasso.load(image).into(binding.imageView)
        }
    }
}

class DiffCallbackRover : DiffUtil.ItemCallback<RoverModel>() {

    override fun areItemsTheSame(oldItem: RoverModel, newItem: RoverModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RoverModel, newItem: RoverModel): Boolean {
        return oldItem == newItem
    }
}