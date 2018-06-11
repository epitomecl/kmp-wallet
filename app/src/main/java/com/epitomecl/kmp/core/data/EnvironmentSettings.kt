package com.epitomecl.kmp.core.data

import com.epitomecl.kmpwallet.BuildConfig
import info.blockchain.wallet.api.Environment
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.params.BitcoinMainNetParams
import org.bitcoinj.params.BitcoinTestNet3Params
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnvironmentSettings @Inject constructor() {

    val environment: Environment = Environment.TESTNET //Environment.fromString(BuildConfig.ENVIRONMENT)

    val bitcoinNetworkParameters: NetworkParameters
        get() = when (environment) {
            Environment.TESTNET -> BitcoinTestNet3Params.get()
            else -> BitcoinMainNetParams.get()
        }
}