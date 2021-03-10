package com.alainp.doordashlite.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.text.NumberFormat.*
import java.util.*

@Entity(tableName = "restaurantDetails")
data class RestaurantDetail @Ignore constructor(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @SerializedName("phone_number") val phoneNumber: String,
    val name: String,
    @SerializedName("is_consumer_subscription_eligible") val dashPass: Boolean,
    @SerializedName("average_rating") val averageRating: Float,
    @SerializedName("number_of_ratings") val numRatings: Int,
    @SerializedName("status_type") val statusType: String,
    @SerializedName("asap_time") val asapTime: Int,
    @SerializedName("cover_img_url") val coverImgUrl: String,
    val status: String,
    val description: String,
    @SerializedName("price_range") val priceRange: Int,
    @Ignore @SerializedName("delivery_fee_details") val deliveryFeeDetails: DeliveryFeeDetails?,
    @Expose(serialize = false, deserialize = false) val lastUpdated: Long? = null,
    @Expose(
        serialize = false,
        deserialize = false
    ) var deliveryFeeDisplayString: String? = deliveryFeeDetails?.originalFee?.displayString
        ?: "$0.00",
) {
    constructor(
        id: Long,
        phoneNumber: String,
        name: String,
        dashPass: Boolean,
        averageRating: Float,
        numRatings: Int,
        statusType: String,
        asapTime: Int,
        coverImgUrl: String,
        status: String,
        description: String,
        priceRange: Int,
        lastUpdated: Long?,
        deliveryFeeDisplayString: String
    ) :
            this(
                id,
                phoneNumber,
                name,
                dashPass,
                averageRating,
                numRatings,
                statusType,
                asapTime,
                coverImgUrl,
                status,
                description,
                priceRange,
                null,
                lastUpdated,
                deliveryFeeDisplayString
            )

    fun averageRatingToString() = averageRating.toString()
}

data class DeliveryFeeDetails(@field:SerializedName("original_fee") val originalFee: OriginalFee)

data class OriginalFee(@SerializedName("display_string") val displayString: String)
