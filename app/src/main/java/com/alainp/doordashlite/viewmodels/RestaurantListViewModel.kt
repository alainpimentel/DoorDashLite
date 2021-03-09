package com.alainp.doordashlite.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alainp.doordashlite.api.RestaurantService
import com.alainp.doordashlite.data.GetRestaurantsResponse
import com.alainp.doordashlite.data.Restaurant
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch

class RestaurantListViewModel : ViewModel() {

    private val _restaurantList = MutableLiveData<List<Restaurant>>()

    val restaurantList = _restaurantList

    val tickerChannel = ticker(delayMillis = 5_000, initialDelayMillis = 0)

    init {
        val service = RestaurantService.create()

        viewModelScope.launch {
            // TODO hook up to repository here
            //_restaurantList
//            val restaurants: List<Restaurant> = (0..10).map { Restaurant(it) }
//            _restaurantList.postValue(restaurants)

            val restaurants: GetRestaurantsResponse = service.getRestaurants(
                lat = 37.422740,
                lng = -122.139956
            )

            _restaurantList.postValue(restaurants.stores)


//            var count = 1
//            for (event in tickerChannel) {
//                val restaurants: List<Restaurant> = (0..count).map { Restaurant(it) }
//                _restaurantList.postValue(restaurants)
//                if (count++ == 10) {
//                    break
//                }
//            }
//
//            tickerChannel.cancel()
        }
    }
}