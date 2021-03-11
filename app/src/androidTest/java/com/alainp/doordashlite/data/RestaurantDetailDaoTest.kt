package com.alainp.doordashlite.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RestaurantDetailDaoTest  {

    private lateinit var database: AppDatabase
    private lateinit var restaurantDetailDao: RestaurantDetailDao
    private val restaurantA = RestaurantDetail(1L, "4155151234", "The Melt", true, 4.5F, 100, "open", 30, "", "status", "Description of the melt", 2, null, 1999, "$3.99")
    private val restaurantB = RestaurantDetail(2L, "4155151235", "The Ice", false, 4.4F, 400, "closed", 60, "", "status", "Description of the ice", 2, null, 2000, "$4.99")
    private val restaurantC = RestaurantDetail(3L, "4155151236", "The Taco", true, 5.0F, 300, "open", 23, "", "status", "Description of the taco", 2, null, 3000, "$1.99")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        restaurantDetailDao = database.restaurantDetailDao()

        // Insert plants in non-alphabetical order to test that results are sorted by name
        restaurantDetailDao.insertAll(listOf(restaurantC, restaurantB, restaurantA))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetRestaurants() = runBlocking {
        val restaurantDetail1 = restaurantDetailDao.getRestaurantDetail(1L)
        Assert.assertThat(restaurantDetail1.firstOrNull(), Matchers.equalTo(restaurantA))

        val restaurantDetail2 = restaurantDetailDao.getRestaurantDetail(3L)
        Assert.assertThat(restaurantDetail2.firstOrNull(), Matchers.equalTo(restaurantC))

        val restaurantDetail3 = restaurantDetailDao.getRestaurantDetail(2L)
        Assert.assertThat(restaurantDetail3.firstOrNull(), Matchers.equalTo(restaurantB))
    }

    @Test
    fun testHasRestaurantDetail() = runBlocking {
        val timeout = 2000L

        // Invalid id should return false
        val hasRestaurantDetailInvalid = restaurantDetailDao.hasRestaurantDetail(4L, timeout)
        Assert.assertFalse(hasRestaurantDetailInvalid)

        // Last updated is greater than or equal to timeout returns false
        val hasRestaurantDetail1 = restaurantDetailDao.hasRestaurantDetail(1L, timeout)
        Assert.assertFalse(hasRestaurantDetail1)

        val hasRestaurantDetail2 = restaurantDetailDao.hasRestaurantDetail(2L, timeout)
        Assert.assertTrue(hasRestaurantDetail2)

        val hasRestaurantDetail3 = restaurantDetailDao.hasRestaurantDetail(3L, timeout)
        Assert.assertTrue(hasRestaurantDetail3)
    }

}