package com.epitomecl.kmpwallet.mvp.login

import com.epitomecl.kmpwallet.mvp.BasePresenter
import com.epitomecl.kmpwallet.mvp.BaseView

object LoginContract {
    interface View : BaseView {
        fun showState(state: String)
    }

    interface Presenter : BasePresenter<View> {
        fun login(id: String, password: String)
    }
}