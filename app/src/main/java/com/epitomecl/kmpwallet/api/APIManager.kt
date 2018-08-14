package com.epitomecl.kmpwallet.api

import android.support.annotation.VisibleForTesting
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.epitomecl.kmpwallet.BuildConfig
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object APIManager {
    private const val SERVER_URI: String = BuildConfig.SERVER_URI

    private lateinit var mTestService: TestService

    init {
        val retrofit = initRetrofit()
        initServices(retrofit)
    }

    @VisibleForTesting
    fun setTestService(testService: TestService) {
        mTestService = testService
    }

    private fun initRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            networkInterceptors().add(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build()
                chain.proceed(request)
            })
            addInterceptor(interceptor)
        }

        return Retrofit.Builder().baseUrl(SERVER_URI)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(createMoshiConverter())
                .client(client.build())
                .build()
    }

    private fun createMoshiConverter(): MoshiConverterFactory = MoshiConverterFactory.create()

    private fun initServices(retrofit: Retrofit) {
        mTestService = retrofit.create(TestService::class.java)

    }

    fun loadTest(param: Number) =
            mTestService.getTest(param)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

    fun login(id: String, pw: String) =
        mTestService.postLogin(id, pw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!

    fun regist(id: String, pw: String) =
            mTestService.postRegist(id, pw)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

    fun balance(xpub: String, api_code: String) =
            mTestService.getBalanceEx(xpub, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun activeReceiveAddress(xpub: String, api_code: String) =
            mTestService.getActiveReceiveAddress(xpub, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun activeChangeAddress(xpub: String, api_code: String) =
            mTestService.getActiveChangeAddress(xpub, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun spendTXOCount(address: String, api_code: String) =
            mTestService.getSpendTXOCount(address, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun pushTX(hashtx: String, api_code: String) =
            mTestService.pushTX(hashtx, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()
}