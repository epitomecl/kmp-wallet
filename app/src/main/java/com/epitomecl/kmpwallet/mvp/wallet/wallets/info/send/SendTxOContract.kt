package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send

import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView
import org.bitcoinj.core.Sha256Hash

object SendTxOContract {
    interface View : BaseView {
        fun onSend()
    }

    interface Presenter : BasePresenter<View> {
        fun makeTx(privKeyString: String, pubKeyString: String, toAddress: String,
                   amount: Long, scriptBytes: String , index: Int, hash: String) : String
        fun pushTx(hashtx: String) : String
    }
}
