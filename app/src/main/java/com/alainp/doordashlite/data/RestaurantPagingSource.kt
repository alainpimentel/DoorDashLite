package com.alainp.doordashlite.data

import android.util.Log
import androidx.paging.PagingSource
import com.alainp.doordashlite.api.RestaurantService
import com.alainp.doordashlite.utilities.PAGE_SIZE

class RestaurantPagingSource(
    private val service: RestaurantService,
    private val lat: Float,
    private val long: Float
) : PagingSource<Int, Restaurant>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Restaurant> {
        val page = params.key ?: 0
        return try {
            val response = service.getRestaurants(lat, long, offset = page * PAGE_SIZE, limit = PAGE_SIZE)
            val totalPages = response.numResults / PAGE_SIZE
            Log.d(TAG, "page $page - totalPages $totalPages - response.numResults ${response.numResults}")
            LoadResult.Page(
                data = response.stores,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (page == totalPages) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val TAG = "MessiPagingSource"
    }
}