package com.novatech.pixabayphotoviewer.presentation.register

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.novatech.pixabayphotoviewer.R
import org.junit.Rule
import org.junit.Test

class RegistrationActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(RegisterActivity::class.java)

    @Test
    fun registerWithValidDetails_navigatesToHome() {
        // Enter registration details
        onView(withId(R.id.emailEditText))
            .perform(typeText("newuser@test.com"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText))
            .perform(typeText("password123"), closeSoftKeyboard())
        onView(withId(R.id.ageEditText))
            .perform(typeText("25"), closeSoftKeyboard())

        // Click the register button
        onView(withId(R.id.registerButton)).perform(click())

        // Verify that the home screen is displayed
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun registerWithInvalidAge_showsError() {
        // Enter invalid age
        onView(withId(R.id.ageEditText))
            .perform(typeText("16"), closeSoftKeyboard())

        // Click the register button
        onView(withId(R.id.registerButton)).perform(click())

        // Verify that an error message is displayed
        onView(withText("Age must be between 18 and 99"))
            .check(matches(isDisplayed()))
    }
}