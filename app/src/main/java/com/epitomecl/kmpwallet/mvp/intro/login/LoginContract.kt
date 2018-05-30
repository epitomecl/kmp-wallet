package com.epitomecl.kmpwallet.mvp.intro.login

import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object LoginContract {
    interface View : BaseView {
        //
    }

    interface Presenter : BasePresenter<View> {
        fun loginUser(id : String, pw : String)
    }
}