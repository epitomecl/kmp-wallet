package com.epitomecl.kmpwallet

import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmp.core.wallet.TXBuilder
import com.epitomecl.kmp.core.wallet.UTXO
import com.epitomecl.kmpwallet.api.APIManager
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
        private var wallet: HDWalletData? = null
        private var result: SendTXResult? = null
        private var utxoList : List<UTXO>? = null
    }

    @Test
    @Throws(Exception::class)
    fun test1CreateWallet() {
        wallet = HDWalletData(CryptoType.BITCOIN_TESTNET, "IntegrationTest")
    }

    @Test
    @Throws(Exception::class)
    fun test2RequestCoinFromFaucet() {
        val receiveAddress: String = wallet?.getAccount(0)!!.cache.receiveAccount
        result = APIManager.coinFromFaucet(receiveAddress, api_cpde)
        System.out.format("test2RequestCoinFromFaucet END")
    }

    @Test
    @Throws(Exception::class)
    fun test3CheckReceiveTransection() {
        val payloadBytes = Hex.decode(result!!.hashtx)
        val tx = Transaction(NetworkParameters.testNet(), payloadBytes)

        while(true) {
            utxoList = APIManager.checkTX(tx.hashAsString, api_cpde)

            if(!utxoList?.isEmpty()!!) {
                break
            }

            Thread.sleep(60000) //excute every one minute
        }
        System.out.format("test3CheckReceiveTransection END")
    }

    @Test
    @Throws(Exception::class)
    fun test4BackCoinToFaucet() {
        Thread.sleep(30000) //wait 30 sec

        val privKeyString: String = wallet?.getAccount(0)!!.xpriv
        val pubKeyString: String = wallet?.getAccount(0)!!.xpub

        val toAddress = APIManager.addressFaucet(api_cpde)

        val receiveAddress = wallet?.getAccount(0)!!.cache.receiveAccount
        val changeAddress = wallet?.getAccount(0)!!.cache.changeAccount

        val utxo: UTXO? = utxoList?.find { v -> v.toAddress.equals(receiveAddress) }
        if(utxo != null) {
            val send_satoshi: Long = utxo!!.value - 100000 //100000 is fee

            val utxos: MutableList<UTXO> = mutableListOf()
            utxos.add(utxo!!)

            val txBuilder: TXBuilder = TXBuilder()
            val hashtx = txBuilder.makeTx(privKeyString, pubKeyString,
                    toAddress.address, changeAddress,
                    send_satoshi, utxos)

            result = APIManager.pushTX(hashtx, api_cpde)
        }
        else {
            assertNull(utxo)
        }

        System.out.format("test4BackCoinToFaucet END")
    }

    @Test
    @Throws(Exception::class)
    fun test5CheckBackTransection() {
        val payloadBytes = Hex.decode(result!!.hashtx)
        val tx = Transaction(NetworkParameters.testNet(), payloadBytes)

        while(true) {
            utxoList = APIManager.checkTX(tx.hashAsString, api_cpde)

            if(!utxoList?.isEmpty()!!) {
                break
            }

            Thread.sleep(60000) //excute every one minute
        }
        System.out.format("test5CheckBackTransection END")
    }

}
