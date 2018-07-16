package com.epitomecl.kmpwallet.data

import com.epitomecl.kmpwallet.model.types.KmpSend
import com.google.gson.JsonObject
import info.blockchain.wallet.payment.SpendableUnspentOutputs
import io.reactivex.Observable
import org.bitcoinj.core.ECKey
import java.math.BigInteger
import javax.inject.Inject
import javax.inject.Singleton


class KmpDataManager constructor() {

    fun send(from : String, to : String, amount: Long, fee: Long) : Observable<JsonObject> {
        var ksend = KmpSend(from, to, amount, fee)

        ///send process
//        return
        val result:Observable<JsonObject> = Observable.fromArray()
        return result
    }


}