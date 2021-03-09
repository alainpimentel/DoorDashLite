package com.alainp.doordashlite.di

import com.alainp.doordashlite.api.RestaurantService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideRestaurantService(): RestaurantService {
        return RestaurantService.create()
    }
}
