package com.epitomecl.kmpwallet.mvp.wallet

import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object WalletContract {
    interface View : BaseView {
        fun onShowWalletList()
        fun onCreateWallet()
        fun onRestoreWallet()
        fun onWalletFromSeed()
        fun onBackupWallet()
        fun onCancelRestore()
    }

    interface Presenter : BasePresenter<View> {
        fun isBackupedWallet(label: String): Boolean
        fun backupWallet(wallet: HDWalletData): Boolean
    }
}
