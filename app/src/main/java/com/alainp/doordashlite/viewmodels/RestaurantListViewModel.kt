package com.alainp.doordashlite.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alainp.doordashlite.data.Restaurant
import com.alainp.doordashlite.data.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * View model for RestaurantListFragment.
 */
@HiltViewModel
class RestaurantListViewModel @Inject internal constructor(
    private val restaurantRepository: RestaurantRepository
) : ViewModel() {

    fun fetchRestaurants(): Flow<PagingData<Restaurant>> {
        return restaurantRepository.getRestaurants(
            lat = 37.42274F,
            lng = -122.139956F
        ).cachedIn(viewModelScope)
    }

    fun toggleLike(restaurantId: Long) {
        restaurantRepository.toggleLike(restaurantId)
    }
}