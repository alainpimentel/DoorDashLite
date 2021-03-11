package com.alainp.doordashlite.viewmodels

import androidx.paging.PagingData
import com.alainp.doordashlite.data.Restaurant
import com.alainp.doordashlite.data.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class RestaurantListViewModelTest {

    private lateinit var viewModel: RestaurantListViewModel

    private val restaurantRepository: RestaurantRepository =
        Mockito.mock(RestaurantRepository::class.java)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = RestaurantListViewModel(restaurantRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testFetchRestaurants() = runBlocking {
        val empty = PagingData.empty<Restaurant>()

        Mockito.`when`(restaurantRepository.getRestaurants(37.42274F, -122.139956F)).thenReturn(
            flow { emit(empty) })
        val firstOrNull = viewModel.fetchRestaurants().firstOrNull()
        Assert.assertNotNull(firstOrNull)
    }

}