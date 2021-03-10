package com.alainp.doordashlite.viewmodels

import androidx.lifecycle.*
import com.alainp.doordashlite.data.RestaurantDetail
import com.alainp.doordashlite.data.RestaurantRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class RestaurantDetailViewModel @AssistedInject internal constructor(
    private val restaurantRepository: RestaurantRepository,
    @Assisted private val restaurantId: Long,
) : ViewModel() {

    private val _restaurantDetail = MutableLiveData<RestaurantDetail>()
    val restaurantDetail: LiveData<RestaurantDetail> = _restaurantDetail

    init {
        viewModelScope.launch {
            val data: RestaurantDetail = restaurantRepository.getRestaurantDetail(restaurantId)
            _restaurantDetail.postValue(data)
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