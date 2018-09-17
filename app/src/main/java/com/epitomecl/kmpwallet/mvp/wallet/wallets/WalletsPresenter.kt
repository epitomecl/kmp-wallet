package com.epitomecl.kmpwallet.mvp.wallet.wallets

import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl

class WalletsPresenter : BasePresenterImpl<WalletsContract.View>(),
        WalletsContract.Presenter {

    override fun initWallets() : List<HDWalletData> {
        return AppData.getHDWallets()
    }
}