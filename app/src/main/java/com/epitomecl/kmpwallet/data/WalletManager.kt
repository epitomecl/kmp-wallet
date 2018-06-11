package com.epitomecl.kmpwallet.data

import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class WalletManager {

    var wallets : MutableList<HDWalletData> = mutableListOf()

    init {

    }

    fun init(json : String?) {
        //var walletJson = "{ 'Wallets': [ {'Seed': 'KGGKGKUKGGKGKGK', 'AccountNum': '1', 'Label': 'Wallet Name1'}, {'Seed': 'KGGKGKUKGGKGKGK', 'AccountNum': '1', 'Label': 'Wallet Name1'} ] }"
        try {
            val objects = JSONObject(json)
            val walletObjects = objects.optJSONArray("Wallets")
            if(walletObjects == null){
                restoreFromJson(objects.getJSONObject("Wallets"))
            }
            else {
                for (jsonIndex in 0..(walletObjects.length() - 1)) {
                    restoreFromJson(walletObjects.getJSONObject(jsonIndex))
                }
            }
        } catch (e : JSONException) {
            //log
        }
    }

    private fun restoreFromJson(jsonObject : JSONObject) {
        var seedHex : String = jsonObject.optString("Seed")
        var accountNum : String = jsonObject.optString("AccountNum")
        var label : String = jsonObject.optString("Label")
        var cryptoType : String = jsonObject.optString("CryptoType")

        var hdWalletData = HDWalletData.restoreFromSeed(CryptoType.valueOf(cryptoType), seedHex, "", label, accountNum.toInt())
        wallets.add(hdWalletData)
    }

    private fun toJson() : String {
        val objects = JSONObject()
        val array = JSONArray()
        wallets.forEach { e -> run {
            val obj = JSONObject()
            obj.put("Seed", e.seedHex)
            obj.put("AccountNum",e.accounts.size)
            obj.put("Label",e.label)
            obj.put("CryptoType",e.cryptoType.toString())

            array.put(obj)
        }}
        objects.put("Wallets", array)
        return objects.toString()
    }

    fun createWallet(cryptoType : CryptoType, label : String) : String {
        var hdWalletData = HDWalletData(cryptoType, label)
        wallets.add(hdWalletData)
        return toJson()
    }
}
