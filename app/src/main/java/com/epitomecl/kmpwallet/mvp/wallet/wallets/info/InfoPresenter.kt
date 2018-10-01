package com.epitomecl.kmpwallet.mvp.wallet.wallets.info

import com.epitomecl.kmp.core.wallet.AccountData
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.model.SendTXResult
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl

class InfoPresenter : BasePresenterImpl<InfoContract.View>(),
        InfoContract.Presenter {

    lateinit var hdWalletData : HDWalletData
    lateinit var accountData : AccountData
    private var txList : List<SendTXResult>? = null

    override fun setHDWallet(hdWalletData : HDWalletData) {
        this.hdWalletData = hdWalletData
    }

    override fun getHDWallet() : HDWalletData {
        return hdWalletData
    }

    override fun setAccount(account : AccountData) {
        this.accountData = account
    }

    override fun getAccount() : AccountData {
        return this.accountData
    }

    override fun addAccount(label : String) {
        this.hdWalletData.addAccount(label)
        AppData.saveHDWallets()
    }

    override fun getSendTXResultList() : List<SendTXResult>? {
        return this.txList
    }

    override fun setSendTXResultList(txList : List<SendTXResult>?) {
        this.txList = txList
    }
}