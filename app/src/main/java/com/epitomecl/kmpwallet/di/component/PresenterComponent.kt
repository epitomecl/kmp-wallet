package com.epitomecl.kmpwallet.di.component

import com.epitomecl.kmpwallet.TestActivity
import com.epitomecl.kmpwallet.mvp.send.SendFragment
import dagger.Subcomponent
import piuk.blockchain.androidcore.injection.PresenterScope

@PresenterScope
@Subcomponent
interface PresenterComponent {
    fun inject(activity : TestActivity)

    fun inject(fragment : SendFragment)
}