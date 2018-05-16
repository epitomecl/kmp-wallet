package com.epitomecl.kmpwallet.mvp.createwallet

import com.epitomecl.kmpwallet.mvp.BasePresenter
import com.epitomecl.kmpwallet.mvp.BaseView

object CreateWalletContract {
    interface View : BaseView {
        //
    }

    interface Presenter : BasePresenter<View> {
        fun createWallet(label : String)
    }
}