package com.epitomecl.kmpwallet.mvp.wallet.wallets.info

import com.epitomecl.kmp.core.wallet.AccountData
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.model.SendTXResult
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object InfoContract {
    interface View : BaseView {
        fun onShowAccounts()
        fun onCreateAccount()
        fun onShowSendTxO()
        fun onSendCancel()
        fun getHDWalletData() : HDWalletData
        fun setAccount(account : AccountData)
        fun getAccount() : AccountData
        fun getSendTXResultList() : List<SendTXResult>?
    }

    interface Presenter : BasePresenter<View> {
        fun setHDWallet(hdWalletData : HDWalletData)
        fun getHDWallet() : HDWalletData
        fun setAccount(accountData : AccountData)
        fun getAccount() : AccountData
        fun addAccount(label : String)
        fun getSendTXResultList() : List<SendTXResult>?
        fun setSendTXResultList(txList : List<SendTXResult>?)
    }
}
