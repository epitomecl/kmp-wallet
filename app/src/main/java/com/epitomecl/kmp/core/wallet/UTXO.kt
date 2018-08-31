package com.epitomecl.kmp.core.wallet

data class UTXO(
    val hash: String,
    val index: Int,
    val value: Long,
    val scriptBytes: String,
    val toAddress: String
) {
    companion object {
        fun satoshiToCoin(satoshi: Long) : Double {
            return (satoshi.toDouble() / 100000000)
        }
    }
}
