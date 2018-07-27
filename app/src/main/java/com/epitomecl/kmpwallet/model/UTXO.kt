package com.epitomecl.kmpwallet.model

import java.math.BigInteger

data class UTXO(
        val hash: String,
        val index: Int,
        val value: Long,
        val scriptBytes: String,
        val toaddress: String
)
