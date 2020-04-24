package com.greatdayhr.support.core

import android.content.SharedPreferences
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.greatdayhr.support.core.di.CoreComponent
import com.greatdayhr.support.core.di.DaggerCoreComponent
import com.greatdayhr.support.core.di.module.ContextModule
import com.greatdayhr.support.core.di.scope.FeatureScope
import dagger.Component
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var responseHandler: ResponseHandler

    @Before
    fun init() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(appContext))
            .build()

        DaggerTestComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }

    @Test
    fun testCoreComponentModule() {
        assertNotNull(preferences)
        assertNotNull(responseHandler)
    }
}

@Component(
    dependencies = [CoreComponent::class]
)
@FeatureScope
interface TestComponent {
    fun inject(testClass: ExampleInstrumentedTest)
}