package com.epitomecl.kmpwallet.mvp.wallet.wallets.info

import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object InfoContract {
    interface View : BaseView {
        fun onShowAccounts()
        fun onShowCreateAccount()
        fun onShowSendTxO()
        fun getHDWalletData() : HDWalletData
    }

    interface Presenter : BasePresenter<View> {
        fun setHDWalletData(hdWalletData : HDWalletData)
        fun getHDWalletData() : HDWalletData
    }
}
