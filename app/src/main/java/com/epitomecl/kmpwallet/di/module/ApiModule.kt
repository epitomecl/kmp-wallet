package com.epitomecl.kmpwallet.di.module

import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.epitomecl.kmp.core.data.EnvironmentSettings
import dagger.Module
import dagger.Provides
import info.blockchain.wallet.payload.PayloadManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    fun providePayloadManager() = PayloadManager.getInstance()

    @Provides
    @Singleton
    fun provideJacksonConverterFactory() : JacksonConverterFactory {
        return JacksonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRxJavaCallAdapterFactory() : RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideEnvironmentSettings() : EnvironmentSettings {
        return EnvironmentSettings()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder().apply {
            networkInterceptors().add(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build()
                chain.proceed(request)
            })
            addInterceptor(interceptor)
        }.build()
    }

    @Provides
    @Singleton
    @Named("api")
    fun provideRetrofitApiInstance(okHttpClient: OkHttpClient, converterFactory: JacksonConverterFactory, rxJavaCallFactory: RxJava2CallAdapterFactory) :Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://elegantuniv.com")
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(rxJavaCallFactory)
                .build()
    }

    @Provides
    @Singleton
    @Named("explorer")
    fun provideRetrofitExplorerInstance(okHttpClient: OkHttpClient, converterFactory: JacksonConverterFactory, rxJavaCallFactory: RxJava2CallAdapterFactory) :Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://elegantuniv.com")
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(rxJavaCallFactory)
                .build()
    }

    @Provides
    @Singleton
    @Named("shapeshift")
    fun provideRetrofitShapeShiftInstance(okHttpClient: OkHttpClient, converterFactory: JacksonConverterFactory, rxJavaCallFactory: RxJava2CallAdapterFactory) :Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://elegantuniv.com")
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(rxJavaCallFactory)
                .build()
    }
}