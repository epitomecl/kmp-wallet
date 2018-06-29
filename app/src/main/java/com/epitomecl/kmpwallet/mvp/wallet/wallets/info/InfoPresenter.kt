package com.epitomecl.kmpwallet.mvp.wallet.wallets.info

import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl

class InfoPresenter : BasePresenterImpl<InfoContract.View>(),
        InfoContract.Presenter {

    lateinit var hdWalletData : HDWalletData

    override fun setHDWalletData(hdWalletData : HDWalletData) {
        this.hdWalletData = hdWalletData
    }

    override fun getHDWalletData() : HDWalletData {
        return hdWalletData
    }

}