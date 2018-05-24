package com.epitomecl.kmpwallet.mvp.login

import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object LoginContract {
    interface View : BaseView {
        fun showState(state: String)
    }

    interface Presenter : BasePresenter<View> {
        fun login(id: String, password: String)
    }
}