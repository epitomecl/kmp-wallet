package com.epitomecl.kmpwallet.mvp.wallet.wallets

import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import com.epitomecl.kmpwallet.mvp.wallet.wallets.WalletsContract

class WalletsPresenter : BasePresenterImpl<WalletsContract.View>(),
        WalletsContract.Presenter {

    override fun initWallets() : List<HDWalletData> {
        return AppData.getHDWallets()
    }
}