package com.epitomecl.kmpwallet.mvp.wallet

import android.support.v4.app.Fragment
import com.epitomecl.kmpwallet.mvp.BasePresenter
import com.epitomecl.kmpwallet.mvp.BaseView

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
