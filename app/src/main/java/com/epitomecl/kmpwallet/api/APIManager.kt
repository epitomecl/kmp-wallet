package com.epitomecl.kmpwallet.api

import android.support.annotation.VisibleForTesting
import com.epitomecl.kmp.core.wallet.IAPIManager
import com.epitomecl.kmpwallet.BuildConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object APIManager : IAPIManager {
    private const val SERVER_URI: String = BuildConfig.SERVER_URI
    private const val BLOCKEXPLORER_URI: String = BuildConfig.BLOCKEXPLORER_URI

    private lateinit var mAPIService: APIService
    private lateinit var mBlockExplorerService: APIService

    init {
        val retrofit = initRetrofit(SERVER_URI)
        val retrofitBlockExplorer = initRetrofit(BLOCKEXPLORER_URI)
        initServices(retrofit, retrofitBlockExplorer)
    }

    @VisibleForTesting
    fun setAPIService(apiService: APIService) {
        mAPIService = apiService
    }

    private fun initRetrofit(baseUri: String): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .apply {
            networkInterceptors().add(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build()
                chain.proceed(request)
            })
            addInterceptor(interceptor)
        }

        return Retrofit.Builder().baseUrl(baseUri)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(createMoshiConverter())
                .client(client.build())
                .build()
    }

    private fun createMoshiConverter(): MoshiConverterFactory = MoshiConverterFactory.create()

    private fun initServices(retrofit: Retrofit, retrofitBlockExplorer: Retrofit) {
        mAPIService = retrofit.create(APIService::class.java)
        mBlockExplorerService = retrofitBlockExplorer.create(APIService::class.java)
    }

    fun login(id: String, pw: String) =
            mAPIService.postLogin(id, pw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())!!

    fun regist(id: String, pw: String) =
            mAPIService.postRegist(id, pw)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

    fun balance(xpub: String, api_code: String) =
            mAPIService.getBalanceEx(xpub, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun activeReceiveAddress(xpub: String, api_code: String) =
            mAPIService.getActiveReceiveAddress(xpub, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun activeChangeAddress(xpub: String, api_code: String) =
            mAPIService.getActiveChangeAddress(xpub, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun spendTXOCount(address: String, api_code: String) =
            mAPIService.getSpendTXOCount(address, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    override fun spendTXOCount(address: String): Int {
        return spendTXOCount(address, "api_code")
    }

    fun pushTX(hashtx: String, api_code: String) =
            mAPIService.pushTX(hashtx, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun getSharingDataList(index: Int, api_code: String) =
            mAPIService.getSharingDataList(index, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun getSharingDataOne(index: Int, label: String, api_code: String) =
            mAPIService.getSharingDataOne(index, label, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun getSharingDataTwo(index: Int, label: String, api_code: String) =
            mAPIService.getSharingDataTwo(index, label, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun backupSharingDataOne(index: Int, label: String, shareddata: String, api_code: String) =
            mAPIService.backupSharingDataOne(index, label, shareddata, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun backupSharingDataTwo(index: Int, label: String, shareddata: String, api_code: String) =
            mAPIService.backupSharingDataTwo(index, label, shareddata, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun getEncrypted(index: Int, label: String, api_code: String) =
            mAPIService.getEncrypted(index, label, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun setEncrypted(index: Int, label: String, encrypted: String, api_code: String) =
            mAPIService.setEncrypted(index, label, encrypted, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    fun getTransactionInfo(txid: String) =
            mBlockExplorerService.getTransactionInfo(txid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    @VisibleForTesting
    fun coinFromFaucet(address: String, api_code: String) =
            mAPIService.coinFromFaucet(address, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    @VisibleForTesting
    fun addressFaucet(api_code: String) =
            mAPIService.addressFaucet(api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()

    @VisibleForTesting
    fun checkTX(txid: String, api_code: String) =
            mAPIService.checkTX(txid, api_code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .blockingSingle()
}
