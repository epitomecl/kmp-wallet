package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send

import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import com.epitomecl.kmp.core.wallet.UTXO
import javax.inject.Inject
import com.epitomecl.kmp.core.wallet.TXBuilder
import com.epitomecl.kmpwallet.model.ActiveAddress
import com.epitomecl.kmpwallet.model.SendTXResult

class SendTxOPresenter @Inject constructor() : BasePresenterImpl<SendTxOContract.View>(),
        SendTxOContract.Presenter {

    override fun makeTx(privKeyString: String, pubKeyString: String, toAddress: String,
                        send_satoshi: Long, utxos: List<UTXO>) : String {

        val changeAddress = getChangeAddress(pubKeyString)

        val txBuilder : TXBuilder = TXBuilder()

        val hashtx = txBuilder.makeTx(privKeyString, pubKeyString,
                                    toAddress, changeAddress,
                                    send_satoshi, utxos)

        return hashtx
    }

    override fun pushTx(hashtx: String) : SendTXResult {
        var result: SendTXResult = APIManager.pushTX(hashtx,"api_code")
        return result
    }

    private fun getChangeAddress(xpub : String) : String {

        val result : ActiveAddress = APIManager.activeChangeAddress(xpub,"api_code")

        return result.address
    }
}
