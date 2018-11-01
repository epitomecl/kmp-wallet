package com.epitomecl.kmpwallet.di.component

import com.epitomecl.kmpwallet.KMPWalletApp
import com.epitomecl.kmpwallet.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = arrayOf(AppModule::class))
interface ApplicationComponent {
    fun presenterComponent(): PresenterComponent

    fun inject(kmpWalletApp : KMPWalletApp)
}