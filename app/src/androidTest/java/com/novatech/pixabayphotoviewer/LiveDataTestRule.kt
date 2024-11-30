package com.novatech.pixabayphotoviewer

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class LiveDataTestRule : TestWatcher() {
    override fun starting(description: Description) {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
            override fun isMainThread(): Boolean = true
            override fun postToMainThread(runnable: Runnable) = runnable.run()
        })
    }

    override fun finished(description: Description) {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}
