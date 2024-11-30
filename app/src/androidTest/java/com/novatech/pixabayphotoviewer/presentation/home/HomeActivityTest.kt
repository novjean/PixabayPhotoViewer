package com.novatech.pixabayphotoviewer.presentation.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.novatech.pixabayphotoviewer.R
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun browseImages_displaysImageList() {
        // Verify that the RecyclerView is displayed
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }

//    @Test
//    fun clickOnImageRow_navigatesToImageDetail() {
//        // Perform click on the first item in the RecyclerView
//        onView(withId(R.id.recyclerView))
//            .perform(RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(0, click()))
//
//        // Verify that the ImageDetail screen is displayed
//        onView(withId(R.id.imageDetailView))
//            .check(matches(isDisplayed()))
//    }
}