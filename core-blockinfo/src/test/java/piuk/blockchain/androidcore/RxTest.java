package piuk.blockchain.androidcore;

import android.support.annotation.CallSuper;

import org.junit.After;
import org.junit.Before;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

/**
 * Class that forces all Rx observables to be subscribed and observed in the same thread through the
 * same Scheduler that runs immediately. Also exposes a {@link TestScheduler} for testing of
 * time-based methods.
 */
public class RxTest {

    private TestScheduler testScheduler;

    @Before
    @CallSuper
    public void setUp() throws Exception {
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
        testScheduler = new TestScheduler();

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> TrampolineScheduler.instance());

        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> TrampolineScheduler.instance());
        RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerCallable -> TrampolineScheduler.instance());
        RxJavaPlugins.setInitSingleSchedulerHandler(schedulerCallable -> TrampolineScheduler.instance());

        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> testScheduler);
    }

    /**
     * Returns a {@link TestScheduler} object which allows for easy testing of time-based methods
     * that return {@link io.reactivex.Observable} objects.
     */
    protected TestScheduler getTestScheduler() {
        return testScheduler;
    }

    @After
    @CallSuper
    public void tearDown() throws Exception {
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }
}
