package com.greatdayhr.support.core.di.module

import android.content.SharedPreferences
import com.greatdayhr.support.core.HttpHeaderInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor

@Module
class HttpInterceptorModule {
    @Provides
    fun logging(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor;
    }

    @Provides
    fun header(preferences: SharedPreferences): HttpHeaderInterceptor =
        HttpHeaderInterceptor(preferences)
}