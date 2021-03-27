package com.alainp.doordashlite.utilities

import android.preference.PreferenceManager
import com.alainp.doordashlite.MainApplication
import com.alainp.doordashlite.data.RestaurantRepository

fun Long.toLikePreferenceKey() = "${RestaurantRepository.PREF_KEY_LIKE}$this"

fun getLike(restaurantId: Long): Boolean {
    val defaultSharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(MainApplication.instance)

    val likeKey = restaurantId.toLikePreferenceKey()
    return defaultSharedPreferences.getBoolean(likeKey, false)
}