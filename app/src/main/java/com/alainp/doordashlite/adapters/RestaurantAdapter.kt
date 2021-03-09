package com.alainp.doordashlite.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alainp.doordashlite.R
import com.alainp.doordashlite.data.Restaurant
import com.alainp.doordashlite.data.asapMinute
import com.alainp.doordashlite.data.isOpen
import com.alainp.doordashlite.databinding.ListItemRestaurantBinding

class RestaurantAdapter :
    PagingDataAdapter<Restaurant, RecyclerView.ViewHolder>(RestaurantDiffCallback()) {
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
        if (restaurant != null) {
            (holder as RestaurantViewHolder).bind(restaurant)
        }
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

            val context = binding.root.context
            val asapMinute = item.asapMinute()
            val asapText = if (asapMinute == null || !item.isOpen()) {
                context.getString(R.string.closed)
            } else {
                "$asapMinute " +
                        if (asapMinute == 1) context.getString(R.string.min)
                        else context.getString(R.string.mins)
            }
            binding.restaurantDistanceText.text = asapText
        }
    }
}

private class RestaurantDiffCallback : DiffUtil.ItemCallback<Restaurant>() {

    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean =
        oldItem == newItem
}
