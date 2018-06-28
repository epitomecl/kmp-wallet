package com.epitomecl.kmpwallet.di.component

import com.epitomecl.kmpwallet.TestActivity
import com.epitomecl.kmpwallet.di.PerActivity
import com.epitomecl.kmpwallet.di.module.ActivityModule
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView
import com.epitomecl.kmpwallet.mvp.send.SendFragment
import dagger.Component
import piuk.blockchain.androidcore.injection.PresenterScope
import javax.inject.Singleton

@PerActivity
//@Component( dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity : TestActivity)

    fun inject(fragment : SendFragment)
}