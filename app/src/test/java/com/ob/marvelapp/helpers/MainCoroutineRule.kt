package com.ob.marvelapp.helpers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.Executors

@Suppress("SENSELESS_COMPARISON")
@ExperimentalCoroutinesApi
class CoroutinesMainDispatcherRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    private val singleThreadExecutor by lazy { Executors.newSingleThreadExecutor() }


    override fun starting(description: Description) {
        super.starting(description)
        if (testDispatcher != null) {
            Dispatchers.setMain(testDispatcher)
        } else {
            Dispatchers.setMain(singleThreadExecutor.asCoroutineDispatcher())
        }
    }

    override fun finished(description: Description) {
        super.finished(description)
        if (singleThreadExecutor != null) {
            singleThreadExecutor.shutdownNow()
        }
        testDispatcher.let {
            testDispatcher.cleanupTestCoroutines()
        }
        Dispatchers.resetMain()
    }
}