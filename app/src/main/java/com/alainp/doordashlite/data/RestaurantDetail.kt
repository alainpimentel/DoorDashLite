package com.alainp.doordashlite.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "restaurantDetails")
data class RestaurantDetail(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @SerializedName("phone_number") val phoneNumber: String,
    val status: String,
    @Expose(serialize = false, deserialize = false) val lastUpdated: Long? = null
)