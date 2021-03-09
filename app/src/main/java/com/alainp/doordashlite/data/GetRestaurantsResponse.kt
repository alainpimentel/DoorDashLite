package com.alainp.doordashlite.data

import com.google.gson.annotations.SerializedName

data class GetRestaurantsResponse (
    @field:SerializedName("num_results") val numResults: Int,
    @field:SerializedName("next_offset") val nextOffset: Int,
    @field:SerializedName("stores") val stores: List<Restaurant>
)