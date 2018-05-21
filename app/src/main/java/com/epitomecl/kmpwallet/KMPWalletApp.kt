package com.epitomecl.kmpwallet

import android.app.Application
import android.content.Context
import com.epitomecl.kmpwallet.di.component.AppComponent
import com.epitomecl.kmpwallet.di.module.AppModule
import com.epitomecl.kmpwallet.di.component.DaggerAppComponent

class KMPWalletApp : Application() {

    val singleton: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        fun getAppComponent(context : Context): AppComponent {
            return (context.applicationContext as KMPWalletApp).singleton
        }

        fun get(context: Context) : KMPWalletApp {
            return context.applicationContext as KMPWalletApp
        }
    }

}