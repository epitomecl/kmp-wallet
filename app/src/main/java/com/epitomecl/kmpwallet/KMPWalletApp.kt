package com.epitomecl.kmpwallet

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.epitomecl.kmpwallet.di.component.AppComponent
import com.epitomecl.kmpwallet.di.module.AppModule
import com.epitomecl.kmpwallet.di.component.DaggerAppComponent

class KMPWalletApp : Application() {

    val singleton : AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this, getSharedPreferences(this.getString(R.string.KMP), Context.MODE_PRIVATE ))).build()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        if (BuildConfig.DEBUG){ //&& !AndroidUtils.is21orHigher()) {
            MultiDex.install(base)
        }
    }

    override fun onCreate() {
        super.onCreate()
        singleton.inject(this)
    }

    companion object {
        fun getAppComponent(context : Context) : AppComponent {
            return (context.applicationContext as KMPWalletApp).singleton
        }

        fun get(context: Context) : KMPWalletApp {
            return context.applicationContext as KMPWalletApp
        }
    }

}