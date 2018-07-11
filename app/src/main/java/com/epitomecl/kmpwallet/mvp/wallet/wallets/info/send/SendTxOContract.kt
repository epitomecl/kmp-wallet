package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send

import com.epitomecl.kmp.core.wallet.AccountData
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object SendTxOContract {
    interface View : BaseView {
        fun onSend()
    }

    interface Presenter : BasePresenter<View> {
        fun send(hashtx: String) : String
    }
}