package com.epitomecl.kmpwallet

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.epitomecl.kmp.core.data.EnvironmentSettings
import com.epitomecl.kmpwallet.di.Injector
import info.blockchain.wallet.BlockchainFramework
import info.blockchain.wallet.FrameworkInterface
import info.blockchain.wallet.api.Environment
import org.bitcoinj.core.NetworkParameters
import piuk.blockchain.androidcore.data.rxjava.RxBus
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import dagger.Lazy

class KMPWalletApp : Application(), FrameworkInterface {

//    val singleton : AppComponent by lazy {
//        DaggerAppComponent.builder()
//                .appModule(AppModule(this, getSharedPreferences(this.getString(R.string.KMP), Context.MODE_PRIVATE )))
//                .build()
//
//    }

    @field:[Inject Named("api")]
    internal lateinit var retrofitApi : Lazy<Retrofit>

    @field:[Inject Named("explorer")]
    internal lateinit var retrofitExplorer : Lazy<Retrofit>

    @field:[Inject Named("shapeshift")]
    internal lateinit var retrofitShapeshift : Lazy<Retrofit>

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
//        singleton.inject(this)
        Injector.getInstance().init(this, getSharedPreferences(this.getString(R.string.KMP), Context.MODE_PRIVATE ))
        Injector.getInstance().getAppComponent().inject(this)
        BlockchainFramework.init(this)

    }

//    companion object {
//        fun getAppComponent(context : Context) : AppComponent {
//            return (context.applicationContext as KMPWalletApp).singleton
//        }
//
//        fun get(context: Context) : KMPWalletApp {
//            return context.applicationContext as KMPWalletApp
//        }
//    }

    // Pass instances to JAR Framework, evaluate after object graph instantiated fully
    override fun getRetrofitApiInstance(): Retrofit {
        return retrofitApi.get()
    }

    override fun getRetrofitExplorerInstance(): Retrofit {
        return retrofitExplorer.get()
    }

    override fun getRetrofitShapeShiftInstance(): Retrofit {
        return retrofitShapeshift.get()
    }

    override fun getEnvironment(): Environment {
        return environmentSettings.environment
    }

    override fun getBitcoinParams(): NetworkParameters {
        return environmentSettings.bitcoinNetworkParameters
    }

    override fun getBitcoinCashParams(): NetworkParameters {
        return environmentSettings.bitcoinCashNetworkParameters
    }

    override fun getApiCode(): String {
        return "25a6ad13-1633-4dfb-b6ee-9b91cdf0b5c3"
    }

    override fun getDevice(): String {
        return "android"
    }

    override fun getAppVersion(): String {
        return BuildConfig.VERSION_NAME
    }


}