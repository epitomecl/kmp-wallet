package com.epitomecl.kmpwallet.mvp.wallet.restore

import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl

class WalletFromSeedPresenter : BasePresenterImpl<WalletFromSeedContract.View>(),
        WalletFromSeedContract.Presenter {

    override fun restoreWallet(cryptoType: CryptoType, seed: String): Boolean {
        try {
            AppData.restoreWallet(cryptoType, seed, "restored from seed")
            AppData.saveHDWallets()
            return true
        } catch (e: Exception) {
            mView?.showError(e.message)
        }
        return false
    }
}
