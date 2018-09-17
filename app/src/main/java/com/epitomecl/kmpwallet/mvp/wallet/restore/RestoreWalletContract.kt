package com.epitomecl.kmpwallet.mvp.wallet.create

import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object RestoreWalletContract {
    interface View : BaseView {
        fun restoreWallet(label: String)
    }

    interface Presenter : BasePresenter<View> {
        fun restoredWallets() : List<String>
        fun restoreWallet(label: String): Boolean
    }
}
