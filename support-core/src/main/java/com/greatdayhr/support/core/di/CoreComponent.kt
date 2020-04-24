package com.greatdayhr.support.core.di

import android.content.Context
import android.content.SharedPreferences
import com.greatdayhr.support.core.ResponseHandler
import com.greatdayhr.support.core.di.module.ContextModule
import com.greatdayhr.support.core.di.module.HttpInterceptorModule
import com.greatdayhr.support.core.di.module.RetrofitModule
import com.greatdayhr.support.core.di.module.StorageModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(modules = [ContextModule::class, RetrofitModule::class, StorageModule::class, HttpInterceptorModule::class])
@Singleton
interface CoreComponent {
    fun getRetrofit(): Retrofit
    fun getSharedPreferences(): SharedPreferences
    fun getResponseHandler(): ResponseHandler
    fun getContext(): Context
}