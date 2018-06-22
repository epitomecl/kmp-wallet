package com.epitomecl.kmp.core.util.rpc

interface RPCClient {

    fun getBlockCount() : Int
    fun validateaddress(address : String) : String

}