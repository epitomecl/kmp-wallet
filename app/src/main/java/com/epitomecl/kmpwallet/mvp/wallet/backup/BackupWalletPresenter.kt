package com.epitomecl.kmpwallet.mvp.wallet.create

import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl

class BackupWalletPresenter : BasePresenterImpl<BackupWalletContract.View>(),
        BackupWalletContract.Presenter {

    override fun restoreWallet(cryptoType : CryptoType, seed: String) {
        AppData.restoreWallet(cryptoType, seed)
        AppData.saveHDWallets()
    }
}
