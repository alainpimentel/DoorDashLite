package com.alainp.doordashlite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.alainp.doordashlite.databinding.FragmentRestaurantDetailBinding
import com.alainp.doordashlite.viewmodels.RestaurantDetailViewModel
import com.alainp.doordashlite.viewmodels.RestaurantDetailViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class RestaurantDetailFragment : Fragment() {

    private val args: RestaurantDetailFragmentArgs by navArgs()
    @Inject
    lateinit var restaurantDetailViewModelViewModelFactory: RestaurantDetailViewModelFactory
    private val viewModel: RestaurantDetailViewModel by viewModels() {
        RestaurantDetailViewModel.provideFactory(restaurantDetailViewModelViewModelFactory, args.restaurantId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        Log.d("messi", "Arg ${args.restaurantId}")
        val binding = DataBindingUtil.inflate<FragmentRestaurantDetailBinding>(
            inflater,
            R.layout.fragment_restaurant_detail,
            container,
            false
        ).apply {
            restaurantDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                    var isToolbarShown = false
                    val shouldShowToolbar = scrollY > toolbar.height
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Use shadow animator to add elevation if toolbar is shown
                        appbar.isActivated = shouldShowToolbar

                        // Show the plant name if toolbar is shown
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                    toolbar.setNavigationOnClickListener { view ->
                        view.findNavController().navigateUp()
                    }
                })

        }

        subscribeUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        view.findViewById<Button>(R.id.button_second).setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }

    private fun subscribeUI() {
        lifecycleScope.launchWhenCreated {
            viewModel.restaurantDetail.observe(viewLifecycleOwner) { restaurantDetail ->
                Log.d("messi","$restaurantDetail")
            }
        }
    }
}