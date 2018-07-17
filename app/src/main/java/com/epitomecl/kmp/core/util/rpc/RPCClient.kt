package com.epitomecl.kmp.core.util.rpc

import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient

interface RPCClient {

    fun getBlockCount() : Int
    fun validateaddress(address : String) : String

    fun getBalance() : Double

    fun sendFrom(fromAccount:String, toAddress:String, amount: Double) : String

    fun getTransaction(txid: String) : BitcoindRpcClient.RawTransaction
}