package com.epitomecl.kmpwallet.mvp.createwallet

import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object CreateWalletContract {
    interface View : BaseView {
        //
    }

    interface Presenter : BasePresenter<View> {
        fun createWallet(label : String)
    }
}