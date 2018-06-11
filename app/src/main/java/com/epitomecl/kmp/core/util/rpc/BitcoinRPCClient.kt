package com.epitomecl.kmp.core.util.rpc

import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient
import com.epitomecl.kmpwallet.BuildConfig
import java.net.MalformedURLException
import java.net.URL

class BitcoinRPCClient : RPCClient {
    private val host = BuildConfig.BTC_RPC_HOST
    private val port = Integer.parseInt(BuildConfig.BTC_RPC_PORT)
    private val user = BuildConfig.BTC_RPC_ID
    private val password = BuildConfig.BTC_RPC_PW

    lateinit var client: BitcoinJSONRPCClient

    init {
        init()
    }

    private fun init() {
        try {
            val url = URL("http://" + user + ':'.toString() + password + "@" + host + ":" + port + "/")
            client = BitcoinJSONRPCClient(url)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    override fun getBlockCount() : Int {
        return client.blockCount
    }

}