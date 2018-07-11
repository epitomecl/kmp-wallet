package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send

import android.widget.Toast
import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl

class SendTxOPresenter : BasePresenterImpl<SendTxOContract.View>(),
        SendTxOContract.Presenter {

    override fun send(hashtx: String) : String {
        var result: String = APIManager.pushTX(hashtx,"api_code")
        return result
    }
}
