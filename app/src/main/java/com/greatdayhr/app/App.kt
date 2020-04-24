package com.greatdayhr.app

import android.app.Application
import com.greatdayhr.support.core.di.CoreComponent
import com.greatdayhr.support.core.di.CoreComponentProvider
import com.greatdayhr.support.core.di.DaggerCoreComponent
import com.greatdayhr.support.core.di.module.ContextModule

class App : Application(), CoreComponentProvider {
    private lateinit var coreComponent: CoreComponent

    override fun provideCoreComponent(): CoreComponent {
        if (!this::coreComponent.isInitialized) {
            coreComponent = DaggerCoreComponent
                .builder()
                .contextModule(ContextModule(this))
                .build()
        }
        return coreComponent
    }
}