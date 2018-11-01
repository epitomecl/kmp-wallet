package com.epitomecl.kmpwallet

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.epitomecl.kmpwallet.data.EnvironmentSettings
import com.epitomecl.kmpwallet.util.rxjava.RxBus
import com.epitomecl.kmpwallet.di.Injector
import org.bitcoinj.core.NetworkParameters
import javax.inject.Inject

class KMPWalletApp : Application() {

    @Inject
    internal lateinit var rxBus: RxBus

    @Inject
    internal lateinit var environmentSettings : EnvironmentSettings

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        if (BuildConfig.DEBUG){ //&& !AndroidUtils.is21orHigher()) {
            MultiDex.install(base)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Injector.getInstance().init(this, getSharedPreferences(this.getString(R.string.KMP), Context.MODE_PRIVATE ))
        Injector.getInstance().getAppComponent().inject(this)
    }

    fun getEnvironment(): NetworkParameters {
        return environmentSettings.environment
    }

    fun getBitcoinParams(): NetworkParameters {
        return environmentSettings.bitcoinNetworkParameters
    }

    fun getApiCode(): String {
        return "25a6ad13-1633-4dfb-b6ee-9b91cdf0b5c3"
    }

    fun getDevice(): String {
        return "android"
    }

    fun getAppVersion(): String {
        return BuildConfig.VERSION_NAME
    }

}