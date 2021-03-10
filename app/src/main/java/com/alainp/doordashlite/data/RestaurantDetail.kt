package com.alainp.doordashlite.data

import com.google.gson.annotations.SerializedName

data class RestaurantDetail(
    val id: Long,
    @SerializedName("phone_number") val phoneNumber: String,
    val status: String
)