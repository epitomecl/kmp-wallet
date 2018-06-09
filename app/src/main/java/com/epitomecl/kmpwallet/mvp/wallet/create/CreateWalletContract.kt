package com.epitomecl.kmpwallet.mvp.wallet.create

import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object CreateWalletContract {
    interface View : BaseView {
        fun init()
        fun onClickCreate()
        fun isValidLabel() : Boolean
        fun createCryptoTypeSpinner()
        fun setCryptoType(type : CryptoType)
    }

    interface Presenter : BasePresenter<View> {
        fun createWallet(type : CryptoType, label : String)
    }
}