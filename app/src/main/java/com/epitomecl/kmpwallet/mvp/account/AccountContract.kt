package com.epitomecl.kmpwallet.mvp.account

import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import com.epitomecl.kmpwallet.mvp.base.BaseView

object AccountContract {
    interface View : BaseView {
        fun onSuccessAccountCreated()
        fun onError(msg : String)
    }

    interface Presenter : BasePresenter<View> {

    }
}