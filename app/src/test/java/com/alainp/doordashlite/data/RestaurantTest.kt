package com.alainp.doordashlite.data

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class RestaurantTest {

    @Test
    fun testDefaultValues() {
        val restaurant = Restaurant(
            1L,
            "The Melt",
            "A description",
            0.0,
            "",
            "2021-03-11T07:45:00Z",
            "2021-03-11T01:32:20Z",
            RestaurantStatus(
                listOf(47, 47)
            )
        )

        assertThat(restaurant.nextCloseTime, equalTo("2021-03-11T07:45:00"))
        assertThat(restaurant.nextOpenTime, equalTo("2021-03-11T01:32:20"))
        assertThat(restaurant.asapMinute(), equalTo(47))
    }
}