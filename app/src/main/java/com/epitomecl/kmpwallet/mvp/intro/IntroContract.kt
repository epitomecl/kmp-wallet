package com.epitomecl.kmpwallet.mvp.intro

import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object IntroContract {
    interface View : BaseView {
        fun showState(state: String)
        fun onLogin()
        fun onRegist()
        fun onChangeWalletActivity()
    }

    interface Presenter : BasePresenter<View> {
        //
    }
}