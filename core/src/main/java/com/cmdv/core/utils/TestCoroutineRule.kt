package com.cmdv.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.ExternalResource

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineRule(
    val testDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
) : ExternalResource() {
    val dispatchers: CoroutineDispatchers by lazy {
        object : CoroutineDispatchers {
            override val io: CoroutineDispatcher
                get() = testDispatcher
            override val main: CoroutineDispatcher
                get() = testDispatcher
            override val default: CoroutineDispatcher
                get() = testDispatcher
        }
    }
    public override fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    public override fun after() {
        Dispatchers.resetMain()
    }
}
interface CoroutineDispatchers {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}
