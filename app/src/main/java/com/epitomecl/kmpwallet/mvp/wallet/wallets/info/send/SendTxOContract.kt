package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send

import com.epitomecl.kmpwallet.model.SendTXResult
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView
import com.epitomecl.kmp.core.wallet.UTXO

object SendTxOContract {
    interface View : BaseView {
        fun onSend()
    }

    interface Presenter : BasePresenter<View> {
        fun makeTx(privKeyString: String, pubKeyString: String, toAddress: String,
                   send_satoshis: Long, utxos: List<UTXO>) : String
        fun pushTx(hashtx: String) : SendTXResult
    }
}
