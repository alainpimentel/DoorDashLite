package com.alainp.doordashlite.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDetailDao {
    @Query("SELECT * FROM restaurantDetails WHERE id = :restaurantId")
    fun getRestaurantDetail(restaurantId: Long): Flow<RestaurantDetail>

    @Query("SELECT EXISTS(SELECT * FROM restaurantDetails WHERE id = :restaurantId AND lastUpdated >= :timeout)")
    fun hasRestaurantDetail(restaurantId: Long, timeout: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<RestaurantDetail>)
}