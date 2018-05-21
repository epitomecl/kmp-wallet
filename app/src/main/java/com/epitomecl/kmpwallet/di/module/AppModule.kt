package com.epitomecl.kmpwallet.di.module

import android.app.Application
import com.epitomecl.kmpwallet.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    private lateinit var mApp: Application

    constructor(application : Application) {
        mApp = application
    }

    @Provides
    fun provideApp() = mApp

    @Provides
    @ApplicationContext
    fun provideAppContext() = mApp

}