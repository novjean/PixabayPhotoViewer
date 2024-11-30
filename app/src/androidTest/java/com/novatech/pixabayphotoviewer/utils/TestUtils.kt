package com.novatech.pixabayphotoviewer.utils

import androidx.test.platform.app.InstrumentationRegistry

object TestUtils {
    @JvmStatic
    fun disableAnimations() {
        val uiAutomation = InstrumentationRegistry.getInstrumentation().uiAutomation
        uiAutomation.executeShellCommand("settings put global transition_animation_scale 0")
        uiAutomation.executeShellCommand("settings put global window_animation_scale 0")
        uiAutomation.executeShellCommand("settings put global animator_duration_scale 0")
    }
}