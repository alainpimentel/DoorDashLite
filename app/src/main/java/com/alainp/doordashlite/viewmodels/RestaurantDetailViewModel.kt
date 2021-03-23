package com.alainp.doordashlite.viewmodels

import android.app.Application
import android.widget.Toast
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
    @Assisted application: Application
) : AndroidViewModel(application) {

    private val _restaurantDetail = MutableLiveData<RestaurantDetail>()
    val restaurantDetail: LiveData<RestaurantDetail> = _restaurantDetail


    init {

        Toast.makeText(application, "Hola", Toast.LENGTH_SHORT).show()
        viewModelScope.launch(Dispatchers.IO) {
            restaurantRepository.getRestaurantDetail(restaurantId).collect {
                _restaurantDetail.postValue(it)
            }
        }
    }

    companion object {
        fun provideFactory(
            assistedFactory: RestaurantDetailViewModelFactory,
            restaurantId: Long,
            application: Application
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(restaurantId, application) as T
            }

        }
    }
}

@AssistedFactory
interface RestaurantDetailViewModelFactory {
    fun create(restaurantId: Long, application: Application): RestaurantDetailViewModel
}