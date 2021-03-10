package com.alainp.doordashlite.di

import android.content.Context
import com.alainp.doordashlite.data.AppDatabase
import com.alainp.doordashlite.data.RestaurantDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideRestaurantDetailDao(appDatabase: AppDatabase): RestaurantDetailDao {
        return appDatabase.restaurantDetailDao()
    }
}
