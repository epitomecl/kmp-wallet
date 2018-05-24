package com.epitomecl.kmpwallet.model.types

import com.google.gson.annotations.Expose

class KmpSend(from : String, to : String, amount: Long, fee: Long) : KmpType.Packer {

    @Expose
    val from : String = ""

    @Expose
    val to : String = ""

    @Expose
    val amount : Long = 0

    @Expose
    val fee : Long = 0

    override fun pack(writer: KmpType.Writer) {
        writer.putString(from)
        writer.putString(to)
        writer.putLongLE(amount)
        writer.putLongLE(fee)
    }
}