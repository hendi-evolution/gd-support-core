package com.greatdayhr.support.core.di.module

import com.greatdayhr.support.core.HttpHeaderInterceptor
import com.greatdayhr.support.core.ResponseHandler
import dagger.Module
import dagger.Provides
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module()
class RetrofitModule {
    @Provides
    @Singleton
    fun httpClient(
        logInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HttpHeaderInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.writeTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.addInterceptor(headerInterceptor)
        builder.addInterceptor(logInterceptor)
        val builderManager = RetrofitUrlManager.getInstance().with(builder)
        return builderManager.build()
    }

    @Provides
    @Singleton
    fun retrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun responseHandler(retrofit: Retrofit) = ResponseHandler(retrofit)
}