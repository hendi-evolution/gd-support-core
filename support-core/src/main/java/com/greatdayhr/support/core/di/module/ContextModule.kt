package com.greatdayhr.support.core.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val app: Context) {
    @Provides
    @Singleton
    fun context(): Context = app.applicationContext
}