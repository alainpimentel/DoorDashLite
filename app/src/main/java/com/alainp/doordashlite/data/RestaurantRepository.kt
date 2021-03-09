package com.alainp.doordashlite.data

import androidx.annotation.FloatRange
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alainp.doordashlite.api.RestaurantService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantRepository @Inject constructor(private val service: RestaurantService) {

    fun getRestaurants(
        @FloatRange(from = -90.0, to = 90.0) lat: Float,
        @FloatRange(from = -180.0, to = 180.0) lng: Float
    ): Flow<PagingData<Restaurant>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = { RestaurantPagingSource(service, lat, lng) }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 25
    }
}