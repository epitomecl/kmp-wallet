package com.epitomecl.kmpwallet.mvp.wallet

import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object WalletContract {
    interface View : BaseView {
        fun onShowWalletList()
        fun onCreateWallet()
        fun onBackupWallet()
        fun onAccount()
    }

    interface Presenter : BasePresenter<View> {
        //
    }
}
