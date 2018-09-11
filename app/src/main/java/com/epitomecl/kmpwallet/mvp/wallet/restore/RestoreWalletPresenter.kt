package com.epitomecl.kmpwallet.mvp.wallet.create

import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl

class RestoreWalletPresenter : BasePresenterImpl<RestoreWalletContract.View>(),
        RestoreWalletContract.Presenter {

    override fun restoredWallets(): List<String> {
        val index: Int = 0

        return APIManager.getSharingDataList(index, "api_code")
    }

    override fun restoreWallet(cryptoType : CryptoType, seed: String) {
        AppData.restoreWallet(cryptoType, seed)
        AppData.saveHDWallets()
    }
}
