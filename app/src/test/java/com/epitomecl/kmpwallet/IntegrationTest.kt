package com.epitomecl.kmpwallet

import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmp.core.wallet.TXBuilder
import com.epitomecl.kmp.core.wallet.UTXO
import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.model.ActiveAddress
import com.epitomecl.kmpwallet.model.SendTXResult
import org.bitcoinj.core.Transaction
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.bitcoinj.core.NetworkParameters
import org.junit.Assert.assertNull
import org.spongycastle.util.encoders.Hex


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class IntegrationTest {

    companion object {
        private var api_cpde: String = "api_code"
        //private var result: SendTXResult? = null
        //private var utxoList : List<UTXO>? = null
    }

    private fun createWallet() : HDWalletData {
        return HDWalletData(CryptoType.BITCOIN_TESTNET, "IntegrationTest")
    }

    private fun activeReceiveAddress(xpub: String) : String {
        val activeAddress: ActiveAddress = APIManager.activeReceiveAddress(xpub, api_cpde)
        return activeAddress.address
    }

    private fun requestCoinFromFaucet(receiveAddress: String) : SendTXResult {
        val result: SendTXResult = APIManager.coinFromFaucet(receiveAddress, api_cpde)
        return result
    }

    private fun bringbackCoinToFaucet(privKeyString: String, pubKeyString: String, toAddress: String, utxo: UTXO, api_code: String) : SendTXResult {
        val changeAddress: ActiveAddress = APIManager.activeChangeAddress(pubKeyString, api_code)

        val send_satoshi: Long = utxo!!.value - 100000 //100000 is fee

        val utxos: MutableList<UTXO> = mutableListOf()
        utxos.add(utxo!!)

        val txBuilder: TXBuilder = TXBuilder()
        val hashtx = txBuilder.makeTx(privKeyString, pubKeyString,
                toAddress, changeAddress.address,
                send_satoshi, utxos)

        return APIManager.pushTX(hashtx, api_cpde)
    }

    private fun transactionOnBlock(address: String, hashtx: String): UTXO? {
        var utxo: UTXO? = null

        val payloadBytes = Hex.decode(hashtx)
        val tx = Transaction(NetworkParameters.testNet(), payloadBytes)

        while(true) {
            val utxoList: List<UTXO> = APIManager.checkTX(tx.hashAsString, api_cpde)

            utxo = utxoList.find { v -> v.toAddress.equals(address) }
            if(utxo != null) {
                break
            }

            Thread.sleep(60000) //excute every one minute
        }
        return utxo
    }

//    @Test
//    @Throws(Exception::class)
//    fun test1CreateWallet() {
//        val wallet: HDWalletData = createWallet()
//        System.out.format("test1CreateWallet END")
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun test2RequestCoinFromFaucet() {
//        val wallet: HDWalletData = createWallet()
//        val receiveAddress: String = activeReceiveAddress(wallet.getAccount(0).xpub)
//        val result: SendTXResult = requestCoinFromFaucet(receiveAddress)
//        System.out.format("test2RequestCoinFromFaucet END")
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun test3CheckReceiveTransection() {
//        val wallet: HDWalletData = createWallet()
//        val receiveAddress: String = activeReceiveAddress(wallet.getAccount(0).xpub)
//        val result: SendTXResult = requestCoinFromFaucet(receiveAddress)
//        val utxo: UTXO? = transactionOnBlock(receiveAddress, result!!.hashtx)
//
//        assertNull(utxo)
//
//        System.out.format("test3CheckReceiveTransection END")
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun test4BackCoinToFaucet() {
//        val wallet: HDWalletData = createWallet()
//        val receiveAddress: String = activeReceiveAddress(wallet.getAccount(0).xpub)
//        var result: SendTXResult = requestCoinFromFaucet(receiveAddress)
//        val utxo: UTXO? = transactionOnBlock(receiveAddress, result!!.hashtx)
//
//        assertNull(utxo)
//
//        Thread.sleep(30000) //wait 30 sec
//
//        val privKeyString: String = wallet?.getAccount(0)!!.xpriv
//        val pubKeyString: String = wallet?.getAccount(0)!!.xpub
//        val toAddress = APIManager.addressFaucet(api_cpde)
//
//        result = bringbackCoinToFaucet(privKeyString, pubKeyString, toAddress.address, utxo!!, api_cpde)
//
//        System.out.format("test4BackCoinToFaucet END")
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun test5CheckBackTransection() {
//        val wallet: HDWalletData = createWallet()
//        val receiveAddress: String = activeReceiveAddress(wallet.getAccount(0).xpub)
//        var result: SendTXResult = requestCoinFromFaucet(receiveAddress)
//        var utxo: UTXO? = transactionOnBlock(receiveAddress, result!!.hashtx)
//
//        assertNull(utxo)
//
//        Thread.sleep(30000) //wait 30 sec
//
//        val privKeyString: String = wallet?.getAccount(0)!!.xpriv
//        val pubKeyString: String = wallet?.getAccount(0)!!.xpub
//        val toAddress = APIManager.addressFaucet(api_cpde)
//
//        result = bringbackCoinToFaucet(privKeyString, pubKeyString, toAddress.address, utxo!!, api_cpde)
//
//        utxo = transactionOnBlock(toAddress.address, result!!.hashtx)
//
//        assertNull(utxo)
//
//        System.out.format("test5CheckBackTransection END")
//    }

    @Test
    @Throws(Exception::class)
    fun testKmpWallet() {
        val wallet: HDWalletData = createWallet()
        val receiveAddress: String = activeReceiveAddress(wallet.getAccount(0).xpub)
        var result: SendTXResult = requestCoinFromFaucet(receiveAddress)
        var utxo: UTXO? = transactionOnBlock(receiveAddress, result!!.hashtx)

        assertNull(utxo)

        Thread.sleep(30000) //wait 30 sec

        val privKeyString: String = wallet?.getAccount(0)!!.xpriv
        val pubKeyString: String = wallet?.getAccount(0)!!.xpub
        val toAddress = APIManager.addressFaucet(api_cpde)

        result = bringbackCoinToFaucet(privKeyString, pubKeyString, toAddress.address, utxo!!, api_cpde)

        utxo = transactionOnBlock(toAddress.address, result!!.hashtx)

        assertNull(utxo)

        System.out.format("testKmpWallet END")
    }
}
