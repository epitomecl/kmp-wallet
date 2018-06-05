package com.epitomecl.kmpwallet.data

import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmp.core.wallet.HDWalletData
import org.json.JSONObject

class WalletManager {

    val wallets : List<HDWalletData> = emptyList()

    init {

    }

    fun init(json : String?) {
//        //var walletJson = "{ 'Wallets': [ {'Seed': 'KGGKGKUKGGKGKGK', 'AccountNum': '1', 'Label': 'Wallet Name1'}, {'Seed': 'KGGKGKUKGGKGKGK', 'AccountNum': '1', 'Label': 'Wallet Name1'} ] }"
//        val objects = JSONObject(json)
//        val walletObjects = objects.optJSONArray("Wallets")
//        for (jsonIndex in 0..(walletObjects.length() - 1)) {
//            var walletObject = walletObjects.getJSONObject(jsonIndex)
//
//            var seed : String = walletObject.optString("Seed")
//            var accountNum : String = walletObject.optString("AccountNum")
//            var label : String = walletObject.optString("Label")
//            var cryptoType : String = walletObject.optString("CryptoType")
//        }
    }

    fun createWallet(cryptoType : CryptoType, label : String) {

        val obj = JSONObject()
        obj.put("Seed","HKHKGKG")
        obj.put("AccountNum","1")
        obj.put("Label","NAME")
        obj.put("CryptoType","BITCOIN")

        val objects = JSONObject()
        objects.putOpt("Wallets", obj)

        val result = objects.toString()
    }
}