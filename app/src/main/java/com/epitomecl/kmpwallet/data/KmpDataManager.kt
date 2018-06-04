package com.epitomecl.kmpwallet.data

import com.epitomecl.kmpwallet.model.types.KmpSend
import com.google.gson.JsonObject
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KmpDataManager {

    @Inject
    constructor()

    fun send(from : String, to : String, amount: Long, fee: Long) : Observable<JsonObject> {
        var ksend = KmpSend(from, to, amount, fee)

        ///send process
//        return
        val result:Observable<JsonObject> = Observable.fromArray()
        return result
    }

}