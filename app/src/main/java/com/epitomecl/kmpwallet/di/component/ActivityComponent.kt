package com.epitomecl.kmpwallet.di.component

import com.epitomecl.kmpwallet.di.PerActivity
import com.epitomecl.kmpwallet.di.module.ActivityModule
import com.epitomecl.kmpwallet.mvp.send.SendFragment
import dagger.Component

@PerActivity
@Component( dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(fragment : SendFragment)
}