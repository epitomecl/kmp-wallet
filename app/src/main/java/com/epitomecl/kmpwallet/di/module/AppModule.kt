package com.epitomecl.kmpwallet.di.module

import android.app.Application
import android.content.SharedPreferences
import com.epitomecl.kmpwallet.util.rxjava.RxBus
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    private var mApp: Application
    private var mAppData: AppData

    constructor(application : Application, sharedPreferences : SharedPreferences) {
        mApp = application
        mAppData = AppData(application, sharedPreferences)
    }

    @Provides
    fun provideApp() = mApp

    @Provides
    @ApplicationContext
    fun provideAppContext() = mApp

    @Provides
    fun provideAppData() = mAppData

    @Provides
    fun rxbus() = RxBus()
}