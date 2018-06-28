package com.epitomecl.kmpwallet.di.component

import com.epitomecl.kmpwallet.KMPWalletApp
import com.epitomecl.kmpwallet.di.module.ApiModule
import com.epitomecl.kmpwallet.di.module.AppModule
import dagger.Component
import info.blockchain.wallet.util.PrivateKeyFactory
import javax.inject.Singleton

@Singleton
@Component( modules = arrayOf(AppModule::class, ApiModule::class))
interface ApplicationComponent {
    fun presenterComponent(): PresenterComponent

    fun inject(kmpWalletApp : KMPWalletApp)

    fun inject(privateKeyFactory: PrivateKeyFactory)


}