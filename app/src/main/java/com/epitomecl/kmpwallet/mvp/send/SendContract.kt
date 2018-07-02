package com.epitomecl.kmpwallet.mvp.send

import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object SendContract {
    interface View : BaseView {
        fun showTransactionSuccess(hash: String, transactionValue: Long, currency: String)
    }

    interface Presenter : BasePresenter<View> {
//        fun send(from : String, to: String, amount: String, fee: Long)
    }
}