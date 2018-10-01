package com.epitomecl.kmpwallet.data

import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.model.SendTXResult
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class WalletManager {

    var wallets : MutableList<HDWalletData> = mutableListOf()
    var transactions : MutableMap<String, MutableList<SendTXResult>> = mutableMapOf()

    init {

    }

    fun init(json_wallet : String?, json_tx : String?) {
        wallets.clear()
        //var json_wallet = "{ 'Wallets': [ {'Seed': 'KGGKGKUKGGKGKGK', 'AccountNum': '1', 'Label': 'Wallet Name1'}, {'Seed': 'KGGKGKUKGGKGKGK', 'AccountNum': '1', 'Label': 'Wallet Name2'} ] }"
        //var json_tx = "{ 'TxList' : [ {'Label: 'Wallet Name1', Transactions: [ {'HashTX', ''}, {'HashTX', ''}, {'HashTX', ''} ]}, {'Label: 'Wallet Name2', Transactions: [ {'HashTX', ''}, {'HashTX', ''}, {'HashTX', ''} ]} ] }"
        try {
            val objects = JSONObject(json_wallet)
            val walletObjects = objects.optJSONArray("Wallets")

            if(walletObjects == null){
                restoreWalletFromJson(objects.getJSONObject("Wallets"))
            }
            else {
                for (jsonIndex in 0..(walletObjects.length() - 1)) {
                    restoreWalletFromJson(walletObjects.getJSONObject(jsonIndex))
                }
            }

            val txListObjects = JSONObject(json_tx)
            restoreTransactionsFromJson(txListObjects)
        } catch (e : JSONException) {
            //log
        }
    }

    private fun restoreWalletFromJson(jsonObject : JSONObject) {
        var seedHex : String = jsonObject.optString("Seed")
        var accountNum : String = jsonObject.optString("AccountNum")
        var label : String = jsonObject.optString("Label")
        var cryptoType : String = jsonObject.optString("CryptoType")

        var hdWalletData = HDWalletData.restoreFromSeed(CryptoType.valueOf(cryptoType), seedHex, "", label, accountNum.toInt())
        wallets.add(hdWalletData)
    }

    private fun restoreTransactionsFromJson(jsonObject : JSONObject) {
        val txList : JSONArray = jsonObject.optJSONArray("TxList")

        for(e in 0..(txList.length() -1)) {
            val list : JSONObject = txList.getJSONObject(e)
            val label : String = list.optString("Label")
            val transactions : JSONArray = list.optJSONArray("Transactions")

            val array : MutableList<SendTXResult> = mutableListOf()
            for(o in 0..(transactions.length() -1)) {
                val hashtx : String = transactions.getJSONObject(o).optString("HashTX")
                val sendTXResult = SendTXResult(hashtx, "")
                array.add(sendTXResult)
            }
            this.transactions.put(label, array)
        }
    }

    fun walletsToJson() : String {
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

    fun txListToJson() : String {
        //var json_tx = "{ 'TxList' : [ {'Label: 'Wallet Name1', Transactions: [ {'HashTX', ''}, {'HashTX', ''}, {'HashTX', ''} ]}, {'Label: 'Wallet Name2', Transactions: [ {'HashTX', ''}, {'HashTX', ''}, {'HashTX', ''} ]} ] }"
        val objects = JSONObject()
        val array = JSONArray()

        transactions.forEach { e -> run {
            val wallet = JSONObject()
            val transactions = JSONArray()

            wallet.put("Label", e.key)

            e.value.forEach { e -> run {
                transactions.put(e.hashtx)
            }}

            wallet.put("Transactions",transactions)

            array.put(wallet)
        }}

        objects.put("TxList", array)
        return objects.toString()
    }

    fun createWallet(cryptoType : CryptoType, label : String) : String {
        var hdWalletData : HDWalletData?
        hdWalletData = findWallet(cryptoType, label)
        if ( hdWalletData == null ) {
            hdWalletData = HDWalletData(cryptoType, label)
            wallets.add(hdWalletData)
        }
        return walletsToJson()
    }

    fun restoreWallet(cryptoType : CryptoType, seed : String, label : String) {
        var seedHex : String = seed
        var accountNum : String = "1"
        var label : String = label
        var cryptoType : String = cryptoType.toString()

        var hdWalletData = HDWalletData.restoreFromSeed(CryptoType.valueOf(cryptoType), seedHex, "", label, accountNum.toInt())
        wallets.add(hdWalletData)
    }

    private fun findWallet(cryptoType : CryptoType, label : String) : HDWalletData? {
        return wallets.find { v -> v.label.equals(label) }
    }

    fun removeWallet(cryptoType : CryptoType, label : String) : Boolean {
        var hdWalletData : HDWalletData?
        hdWalletData = findWallet(cryptoType, label)
        if ( hdWalletData != null ) {
            wallets.remove(hdWalletData)
            return true
        }
        return false
    }
}
