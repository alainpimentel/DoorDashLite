package com.alainp.doordashlite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.alainp.doordashlite.adapters.RestaurantAdapter
import com.alainp.doordashlite.databinding.FragmentRestaurantListBinding
import com.alainp.doordashlite.viewmodels.RestaurantListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class RestaurantListFragment : Fragment() {

    private val viewModel: RestaurantListViewModel by viewModels()
    private lateinit var binding: FragmentRestaurantListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.restaurantList.addItemDecoration(
            DividerItemDecoration(
                binding.restaurantList.context,
                DividerItemDecoration.VERTICAL
            )
        )

        val restaurantAdapter = RestaurantAdapter()

        binding.restaurantList.adapter = restaurantAdapter
        subscribeUI(restaurantAdapter)

        setHasOptionsMenu(false)

        restaurantAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
            binding.loadingBackground.isVisible = loadState.refresh is LoadState.Loading
        }
        return binding.root
    }

    private fun subscribeUI(restaurantAdapter: RestaurantAdapter) {
        lifecycleScope.launchWhenCreated {
            viewModel.fetchRestaurants().collectLatest {
                restaurantAdapter.submitData(it)
            }
        }
    }
}