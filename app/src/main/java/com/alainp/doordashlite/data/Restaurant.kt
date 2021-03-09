package com.alainp.doordashlite.data

import android.util.Log
import com.alainp.doordashlite.utilities.toEpochMs
import com.google.gson.annotations.SerializedName

data class Restaurant(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("distance_from_consumer") val distanceFromConsumer: Double,
    @SerializedName("cover_img_url") val coverImageUrl: String,
    @SerializedName("next_close_time") val _nextCloseTime: String,
    @SerializedName("next_open_time") val _nextOpenTime: String,
    val status: RestaurantStatus
) {
    val nextCloseTime: String
        get() = _nextCloseTime.dropLast(1)
    val nextOpenTime: String
        get() = _nextOpenTime.dropLast(1)
}

fun Restaurant.asapMinute(): Int? = status.asapMinutesRange.firstOrNull()
fun Restaurant.isOpen(): Boolean {
    val currentTime = System.currentTimeMillis()
    val openTime = nextOpenTime.toEpochMs()
    val closeTime = nextCloseTime.toEpochMs()

    return currentTime in openTime..closeTime
}

class RestaurantStatus(
    @SerializedName("asap_minutes_range") val asapMinutesRange: List<Int>,
)