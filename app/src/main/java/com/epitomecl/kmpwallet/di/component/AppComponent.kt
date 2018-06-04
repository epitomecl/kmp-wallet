package com.epitomecl.kmpwallet.di.component

import android.app.Application
import android.content.Context
import com.epitomecl.kmpwallet.KMPWalletApp
import com.epitomecl.kmpwallet.data.KmpDataManager
import com.epitomecl.kmpwallet.di.ApplicationContext
import com.epitomecl.kmpwallet.di.module.AppModule
import com.epitomecl.kmpwallet.mvp.send.SendFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(kmpWalletApp : KMPWalletApp)

//    @ApplicationContext
//    fun context(): Context

    fun application() : Application
    fun dataManager() : KmpDataManager
}
