package com.alainp.doordashlite.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alainp.doordashlite.api.RestaurantService
import com.alainp.doordashlite.data.Restaurant
import com.alainp.doordashlite.data.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RestaurantListViewModel @Inject internal constructor(
    private val restaurantRepository: RestaurantRepository
) : ViewModel() {

//    private val _restaurantList = MutableLiveData<List<Restaurant>>()
//    val restaurantList = _restaurantList
//    //val tickerChannel = ticker(delayMillis = 5_000, initialDelayMillis = 0)

    init {
        val service = RestaurantService.create()

//        viewModelScope.launch {
//            // TODO hook up to repository here
//            //_restaurantList
////            val restaurants: List<Restaurant> = (0..10).map { Restaurant(it) }
////            _restaurantList.postValue(restaurants)
//
//            val restaurants: GetRestaurantsResponse = restaurantRepository.getRestaurants(
//                lat = 37.42274F,
//                lng = -122.139956F
//            )
//
//            _restaurantList.postValue(restaurants.stores)


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

    fun fetchRestaurants(): Flow<PagingData<Restaurant>> {
        return restaurantRepository.getRestaurants(
            lat = 37.42274F,
            lng = -122.139956F
        ).cachedIn(viewModelScope)
    }
}