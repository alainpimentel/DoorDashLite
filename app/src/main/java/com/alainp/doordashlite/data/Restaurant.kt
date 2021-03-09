package com.alainp.doordashlite.data

import com.google.gson.annotations.SerializedName

data class Restaurant(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("distance_from_consumer") val distanceFromConsumer: Double,
    @SerializedName("cover_img_url") val coverImageUrl: String
)