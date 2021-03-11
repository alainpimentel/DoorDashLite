package com.alainp.doordashlite

import android.os.SystemClock
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class MainActivityTest {

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Test
    fun clickItem_OpensRestaurantDetail() {
        SystemClock.sleep(1500);

        tapOnRecyclerView(R.id.restaurant_list, 0)
        SystemClock.sleep(1500);

        onView(withId(R.id.restaurant_detail_scrollview)).check(matches(isDisplayed()))
    }

    private fun tapOnRecyclerView(@IdRes resId: Int, position: Int) = onView(withId(resId))
        .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()));
}