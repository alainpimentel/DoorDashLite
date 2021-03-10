package com.alainp.doordashlite.data

import android.util.Log
import androidx.annotation.FloatRange
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alainp.doordashlite.api.RestaurantService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantRepository @Inject constructor(
    private val service: RestaurantService,
    private val restaurantDetailDao: RestaurantDetailDao
) {

    // TODO pass page size
    fun getRestaurants(
        @FloatRange(from = -90.0, to = 90.0) lat: Float,
        @FloatRange(from = -180.0, to = 180.0) lng: Float
    ): Flow<PagingData<Restaurant>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = { RestaurantPagingSource(service, lat, lng) }
        ).flow
    }

    suspend fun getRestaurantDetail(restaurantId: Long): Flow<RestaurantDetail> {
        refreshRestaurantDetail(restaurantId)
        return restaurantDetailDao.getRestaurantDetail(restaurantId)
    }

    private suspend fun refreshRestaurantDetail(restaurantId: Long) {
        val currentTimeMillis = System.currentTimeMillis()
        val exists = restaurantDetailDao.hasRestaurantDetail(restaurantId, currentTimeMillis - 1 * 60 * 1000)
        if (!exists) {
            Log.d("messi", "Restaurant $restaurantId does not exist, fetching...")
            val response = service.getRestaurantDetail(restaurantId)
            val updatedRestaurantDetail = response.copy(lastUpdated = System.currentTimeMillis())
            // TODO check for errors

            restaurantDetailDao.insertAll(listOf(updatedRestaurantDetail))
            Log.d("messi", "Restaurant $restaurantId was fetched...!\n $updatedRestaurantDetail")
        } else {
            Log.d("messi", "CACHED!")
        }
    }

    companion object {
        private const val PAGE_SIZE = 25
    }
}