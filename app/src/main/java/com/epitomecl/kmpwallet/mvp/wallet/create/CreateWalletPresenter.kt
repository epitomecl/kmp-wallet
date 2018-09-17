package com.epitomecl.kmpwallet.mvp.wallet.create

import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.model.SecretSharingVO
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl

class CreateWalletPresenter : BasePresenterImpl<CreateWalletContract.View>(),
        CreateWalletContract.Presenter {

    override fun createWallet(cryptoType : CryptoType, label: String) {
        val index: Int = AppData.getUserIndex()!!
        val result: SecretSharingVO = APIManager.getSharingDataOne(index, label, "api_code")
        if(result.label == null) {
            AppData.createWallet(cryptoType, label)
        }
        else {
            mView?.showError(R.string.msg_restored_wallet_label_exist)
        }
    }
}
