package com.alainp.doordashlite.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alainp.doordashlite.data.Restaurant
import com.alainp.doordashlite.databinding.ListItemRestaurantBinding

class RestaurantAdapter :
    ListAdapter<Restaurant, RecyclerView.ViewHolder>(RestaurantDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RestaurantViewHolder(
            ListItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val restaurant = getItem(position)
        (holder as RestaurantViewHolder).bind(restaurant)
    }

    class RestaurantViewHolder(
        private val binding: ListItemRestaurantBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            // TODO set click listener
        }

        fun bind(item: Restaurant) {
            binding.apply {
                restaurant = item
                executePendingBindings()
            }
        }
    }
}

private class RestaurantDiffCallback : DiffUtil.ItemCallback<Restaurant>() {

    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean =
        false //TODO return oldItem.plantId == newItem.plantId

    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean =
        oldItem == newItem
}
