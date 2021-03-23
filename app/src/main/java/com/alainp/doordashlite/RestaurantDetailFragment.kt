package com.alainp.doordashlite

import android.app.Application
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alainp.doordashlite.databinding.FragmentRestaurantDetailBinding
import com.alainp.doordashlite.viewmodels.RestaurantDetailViewModel
import com.alainp.doordashlite.viewmodels.RestaurantDetailViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat.getNumberInstance
import java.util.*
import javax.inject.Inject

/**
 * A [Fragment] to show the restaurant detail.
 * Improvements:
 * * Add more restaurant details
 */
@AndroidEntryPoint
class RestaurantDetailFragment : Fragment() {

    private val args: RestaurantDetailFragmentArgs by navArgs()

    @Inject
    lateinit var restaurantDetailViewModelViewModelFactory: RestaurantDetailViewModelFactory
    private val viewModel: RestaurantDetailViewModel by viewModels() {
        RestaurantDetailViewModel.provideFactory(
            restaurantDetailViewModelViewModelFactory,
            args.restaurantId,
            MainApplication.instance
        )
    }
    private lateinit var binding: FragmentRestaurantDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate<FragmentRestaurantDetailBinding>(
            inflater,
            R.layout.fragment_restaurant_detail,
            container,
            false
        )
        subscribeUI()
        return binding.root
    }


    private fun subscribeUI() {
        lifecycleScope.launchWhenCreated {

            viewModel.restaurantDetail.observe(viewLifecycleOwner) { restaurantDetail ->
                binding.progressBar.visibility = View.GONE
                binding.loadingBackground.visibility = View.GONE
                binding.restaurantDetail = restaurantDetail

                val price = (1..restaurantDetail.priceRange).joinToString(separator = "") { "$" }
                val ratings = getNumberInstance(Locale.US).format(restaurantDetail.numRatings)
                val ratingsText = String.format(getString(R.string.ratings_description), ratings, price)
                binding.restaurantReviewsText.text = ratingsText
                binding.dashPassText.visibility =
                    if (restaurantDetail.dashPass) View.VISIBLE
                    else View.GONE

                val deliveryText = String.format(getString(R.string.delivery_description), restaurantDetail.asapTime)
                binding.restaurantDeliveryTimeValueText.text = deliveryText
                binding.restaurantDeliveryFeeValueText.text = restaurantDetail.deliveryFeeDisplayString
            }
        }
    }
}