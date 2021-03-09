package com.alainp.doordashlite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alainp.doordashlite.adapters.RestaurantAdapter
import com.alainp.doordashlite.databinding.FragmentRestaurantListBinding
import com.alainp.doordashlite.viewmodels.RestaurantListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/**
 * TODO A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class RestaurantListFragment : Fragment() {

    private val viewModel: RestaurantListViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRestaurantListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val restaurantAdapter = RestaurantAdapter()
        binding.restaurantList.adapter = restaurantAdapter
        subscribeUI(restaurantAdapter)

        setHasOptionsMenu(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    private fun subscribeUI(restaurantAdapter: RestaurantAdapter) {
        lifecycleScope.launchWhenCreated {
            viewModel.fetchRestaurants().collectLatest {
                restaurantAdapter.submitData(it)
            }
        }
//        viewModel.fetchRestaurants().
//        viewModel.restaurantList.observe(viewLifecycleOwner) { restaurants ->
//            restaurantAdapter.submitList(restaurants)
//        }
    }
}