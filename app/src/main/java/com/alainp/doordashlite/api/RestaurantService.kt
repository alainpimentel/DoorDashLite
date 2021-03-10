package com.alainp.doordashlite.api

import com.alainp.doordashlite.data.GetRestaurantsResponse
import com.alainp.doordashlite.data.RestaurantDetail
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestaurantService {

    @GET("/v1/store_feed")
    suspend fun getRestaurants(
        @Query("lat") lat: Float,
        @Query("lng") lng: Float,
        @Query("offset") offset: Int = DEFAULT_LIMIT,
        @Query("limit") limit: Int = DEFAULT_OFFSET
    ): GetRestaurantsResponse

    @GET("v2/restaurant/{id}")
    suspend fun getRestaurantDetail(@Path("id") restaurantId: Long): RestaurantDetail

    companion object {
        private const val BASE_URL = "https://api.doordash.com/"
        private const val DEFAULT_OFFSET = 0
        private const val DEFAULT_LIMIT = 50

        fun create(): RestaurantService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RestaurantService::class.java)
        }
    }
}