package com.alainp.doordashlite.data

import androidx.paging.PagingSource
import com.alainp.doordashlite.api.RestaurantService

class RestaurantPagingSource(
    private val service: RestaurantService,
    private val lat: Float,
    private val long: Float
) : PagingSource<Int, Restaurant>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Restaurant> {
        val page = params.key ?: 0
        return try {
            val response = service.getRestaurants(lat, long, offset = page * 50, limit = 50)
            val totalPages = response.numResults / 50
            LoadResult.Page(
                data = response.stores,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (page == totalPages) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}