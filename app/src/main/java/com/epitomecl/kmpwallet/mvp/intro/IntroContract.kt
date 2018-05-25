package com.epitomecl.kmpwallet.mvp.intro

import com.epitomecl.kmpwallet.mvp.BasePresenter
import com.epitomecl.kmpwallet.mvp.BaseView

object IntroContract {
    interface View : BaseView {
        fun showState(state: String)
        fun onLogin()
        fun onRegist()
    }

    interface Presenter : BasePresenter<View> {
        fun login(id: String, password: String)
    }
}