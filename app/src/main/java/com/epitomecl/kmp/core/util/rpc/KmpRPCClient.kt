package com.epitomecl.kmp.core.util.rpc

import com.epitomecl.kmp.core.wallet.CryptoType

object KmpRPCClient {

    private lateinit var btc: BitcoinRPCClient

    init {
        init()
    }

    private fun init() {
        btc = BitcoinRPCClient()
    }

    fun get(type : CryptoType) : RPCClient? {
        return when(type) {
            CryptoType.BITCOIN, CryptoType.BITCOIN_TESTNET -> btc
            else -> null
        }
    }
}