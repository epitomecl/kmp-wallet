package com.epitomecl.kmpwallet.di.module

import android.support.v7.app.AppCompatActivity
import com.epitomecl.kmpwallet.di.ActivityContext
import dagger.Module
import dagger.Provides
import info.blockchain.wallet.util.PrivateKeyFactory

@Module
class ActivityModule {
    private lateinit var mActivity: AppCompatActivity

    constructor(activity : AppCompatActivity) {
        mActivity = activity
    }

    @Provides
    @ActivityContext
    fun provideContext() = mActivity

    @Provides
    fun provideActivity() = mActivity
}