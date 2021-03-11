package com.alainp.doordashlite.viewmodels

import androidx.lifecycle.*
import com.alainp.doordashlite.data.RestaurantDetail
import com.alainp.doordashlite.data.RestaurantRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * View model for RestaurantDetailFragment.
 */
class RestaurantDetailViewModel @AssistedInject internal constructor(
    private val restaurantRepository: RestaurantRepository,
    @Assisted private val restaurantId: Long,
) : ViewModel() {

    private val _restaurantDetail = MutableLiveData<RestaurantDetail>()
    val restaurantDetail: LiveData<RestaurantDetail> = _restaurantDetail


    init {
        viewModelScope.launch(Dispatchers.IO) {
            restaurantRepository.getRestaurantDetail(restaurantId).collect {
                _restaurantDetail.postValue(it)
            }
        }
    }

    companion object {
        fun provideFactory(
            assistedFactory: RestaurantDetailViewModelFactory,
            restaurantId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(restaurantId) as T
            }

        }
    }
}

@AssistedFactory
interface RestaurantDetailViewModelFactory {
    fun create(restaurantId: Long): RestaurantDetailViewModel
}