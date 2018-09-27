package com.epitomecl.kmpwallet.mvp.wallet.restore

import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object WalletFromSeedContract {
    interface View : BaseView {
        fun onClickRestore()
        fun isValidLabel(): Boolean
    }

    interface Presenter : BasePresenter<View> {
        fun restoreWallet(cryptoType: CryptoType, seed: String): Boolean
    }
}
