package com.alainp.doordashlite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alainp.doordashlite.R
import com.alainp.doordashlite.RestaurantListFragmentDirections
import com.alainp.doordashlite.data.Restaurant
import com.alainp.doordashlite.data.asapMinute
import com.alainp.doordashlite.data.isOpen
import com.alainp.doordashlite.databinding.ListItemRestaurantBinding
import com.alainp.doordashlite.utilities.getLike

class RestaurantAdapter(private val onLikeClickCallback: OnLikeClickCallback) :
    PagingDataAdapter<Restaurant, RecyclerView.ViewHolder>(RestaurantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RestaurantViewHolder(
            ListItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onLikeClickCallback
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val restaurant = getItem(position)
        if (restaurant != null) {
            (holder as RestaurantViewHolder).bind(restaurant)
        }
    }

    class RestaurantViewHolder(
        private val binding: ListItemRestaurantBinding,
        private val onLikeClickCallback: OnLikeClickCallback
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.restaurant?.let {
                    val direction =
                        RestaurantListFragmentDirections.actionRestaurantListFragmentToRestaurantDetailFragment(
                            it.id
                        )
                    view.findNavController().navigate(direction)
                }
            }

            binding.likeButton.setOnClickListener {
                binding.restaurant?.let { restaurant ->
                    onLikeClickCallback.onClick(restaurant.id)
                    updateLikeResource(binding.root.context, restaurant.id)
                }
            }
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
            binding.restaurantAsapText.text = asapText

            // Set like state
            updateLikeResource(context, item.id)
        }

        private fun updateLikeResource(context: Context, id: Long) {
            val likeValue = getLike(id)
            val likeResource =
                if (likeValue) R.drawable.ic_baseline_star_24
                else R.drawable.ic_baseline_star_border_24
            binding.likeButton.setImageDrawable(context.getDrawable(likeResource))
        }
    }
}

interface OnLikeClickCallback {
    fun onClick(restaurantId: Long)
}

private class RestaurantDiffCallback : DiffUtil.ItemCallback<Restaurant>() {

    override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean =
        oldItem == newItem
}
