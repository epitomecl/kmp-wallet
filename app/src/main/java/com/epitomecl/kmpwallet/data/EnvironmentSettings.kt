package com.epitomecl.kmpwallet.data

import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.params.TestNet3Params
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnvironmentSettings @Inject constructor() {

    val environment: NetworkParameters = TestNet3Params.get() //Environment.fromString(BuildConfig.ENVIRONMENT)

    val bitcoinNetworkParameters: NetworkParameters
        get() = when (environment) {
            environment -> TestNet3Params.get()
            else -> MainNetParams.get()
        }
}