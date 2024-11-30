package com.novatech.pixabayphotoviewer.matchers

import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ToastMatcher : TypeSafeMatcher<Root>() {

    override fun describeTo(description: Description) {
        description.appendText("is a Toast")
    }

    override fun matchesSafely(root: Root): Boolean {
        val type = root.windowLayoutParams?.get()?.type
        if (type == WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY || type == WindowManager.LayoutParams.TYPE_APPLICATION) {
            val windowToken = root.decorView.windowToken
            val appToken = root.decorView.applicationWindowToken
            // Match if the window tokens are the same (Toast is part of the same application)
            return windowToken == appToken
        }
        return false
    }
}