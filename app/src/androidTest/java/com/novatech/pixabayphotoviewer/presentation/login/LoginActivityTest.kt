package com.novatech.pixabayphotoviewer.presentation.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.novatech.pixabayphotoviewer.R
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun loginWithValidCredentials_navigatesToHome() {
        // Enter email and password
        onView(withId(R.id.emailEditText))
            .perform(typeText("test@test.com"), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText))
            .perform(typeText("password123"), closeSoftKeyboard())

        // Click the login button
        onView(withId(R.id.loginButton)).perform(click())

        // Verify that the home screen is displayed
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }

//    @Test
//    fun loginWithInvalidCredentials_showsError() {
//        // Enter invalid email and password
//        onView(withId(R.id.emailEditText))
//            .perform(typeText("invalid@test.com"), closeSoftKeyboard())
//        onView(withId(R.id.passwordEditText))
//            .perform(typeText("wrongpassword"), closeSoftKeyboard())
//
//        // Click the login button
//        onView(withId(R.id.loginButton)).perform(click())
//
//        // Verify that the Toast with the error message is displayed
////        onView(withText("Invalid login credentials"))
////            .inRoot(ToastMatcher()) // Use the custom ToastMatcher
////            .check(matches(isDisplayed()))
//
//        // Verify that an error message is displayed
//        onView(withText("Invalid login credentials"))
//            .check(matches(isDisplayed()))
//
//    }
}