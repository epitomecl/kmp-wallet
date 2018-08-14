package com.epitomecl.kmpwallet

import android.support.annotation.CallSuper
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.TrampolineScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before

open class RxTestScheduler {
    private var testScheduler: TestScheduler? = null

    private fun reset() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }

    @Before
    @CallSuper
    open fun setUp() {
        reset()

        testScheduler = TestScheduler()

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { schedulerCallable -> TrampolineScheduler.instance() }

        RxJavaPlugins.setInitIoSchedulerHandler { schedulerCallable -> TrampolineScheduler.instance() }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { schedulerCallable -> TrampolineScheduler.instance() }
        RxJavaPlugins.setInitSingleSchedulerHandler { schedulerCallable -> TrampolineScheduler.instance() }

        RxJavaPlugins.setComputationSchedulerHandler { scheduler -> testScheduler }
    }

    protected fun getTestScheduler(): TestScheduler? {
        return testScheduler
    }

    @After
    @CallSuper
    @Throws(Exception::class)
    fun tearDown() {
        reset()
    }
}