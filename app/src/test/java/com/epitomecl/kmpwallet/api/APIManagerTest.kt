package com.epitomecl.kmpwallet.api

import com.epitomecl.kmp.core.wallet.AccountKeyDerivation
import com.epitomecl.kmpwallet.model.UserVO
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertArrayEquals
import com.epitomecl.kmpwallet.RxTestScheduler
import com.epitomecl.kmpwallet.model.ActiveAddress
import com.epitomecl.kmpwallet.model.SendTXResult
import com.epitomecl.kmp.core.wallet.UTXO
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send.SendTxOPresenter
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send.SendTxOPresenter_Factory
import org.junit.Assert.assertEquals
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters
import org.mockito.ArgumentMatchers.anyString

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class APIManagerTest : RxTestScheduler() {

    private val apiService: APIService = mock()

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        APIManager.setAPIService(apiService)
    }

    @Test
    @Throws(Exception::class)
    fun test1LoginAPI() {
        //System.out.println("loginAPITest BEGIN")

        val id = "aaaa"
        val pw = "aaaa"
        val userVO = UserVO("user_session", 1, id)

        whenever(apiService.postLogin(id, pw))
                .thenReturn(Observable.just(userVO))

        val testObserver = APIManager.login(id, pw).test()

        verify(apiService).postLogin(id, pw)
        verifyNoMoreInteractions(apiService)
        testObserver.assertComplete()
        testObserver.assertValue(userVO)

        //System.out.println("loginAPITest END")
    }

    @Test
    @Throws(Exception::class)
    fun test2RegistAPI() {
        val id = "aaaa"
        val pw = "aaaa"
        val userVO = UserVO("user_session", 1, id)

        whenever(apiService.postRegist(id, pw))
                .thenReturn(Observable.just(userVO))

        val testObserver = APIManager.regist(id, pw).test()

        verify(apiService).postRegist(id, pw)
        verifyNoMoreInteractions(apiService)
        testObserver.assertComplete()
        testObserver.assertValue(userVO)
    }

    @Test
    @Throws(Exception::class)
    fun test3BalanceAPI() {
        val xpub = "tpubDDZWXtva1hncbfjckydHWua6Tp6gt8JPNrhQw63kGvSp6NeMQkiBbsQ3iofX1MUCR8vZzpwcKSLVdTSBtGLpDvQTnkxN5azfugVxJYG3Ytj"
        val api_code = "api_code"
        val utxolist = mutableListOf<UTXO>()//mock(mutableListOf<UTXO>()::class)
        utxolist.add(UTXO("hash-string", 0, 10000, "scriptBytes-string", "mv4b1aMUXTonnX2MaBnkxTzwd76tykykTa"))
        utxolist.add(UTXO("hash-string", 0, 10000, "scriptBytes-string", "myGTTCSyiJasZYfFXtfYx7VvV3Pc9ao5N3 "))
        utxolist.add(UTXO("hash-string", 0, 10000, "scriptBytes-string", "mrBAtbFHhvj2zHxwETwiL9Hg818xGRA97h  "))

        whenever(apiService.getBalanceEx(xpub, api_code))
                .thenReturn(Observable.just(utxolist))

        val resultList : List<UTXO> = APIManager.balance(xpub, api_code)

        verify(apiService).getBalanceEx(xpub, api_code)
        verifyNoMoreInteractions(apiService)

        assertArrayEquals(utxolist.toTypedArray(), resultList.toTypedArray())
    }

    @Test
    @Throws(Exception::class)
    fun test4ActiveReceiveAddressAPI() {
        val xpub = "tpubDDZWXtva1hncbfjckydHWua6Tp6gt8JPNrhQw63kGvSp6NeMQkiBbsQ3iofX1MUCR8vZzpwcKSLVdTSBtGLpDvQTnkxN5azfugVxJYG3Ytj"
        val api_code = "api_code"
        val receive_address = ActiveAddress("mv4b1aMUXTonnX2MaBnkxTzwd76tykykTa")

        whenever(apiService.getActiveReceiveAddress(xpub, api_code))
                .thenReturn(Observable.just(receive_address))

        val result : ActiveAddress = APIManager.activeReceiveAddress(xpub, api_code)

        verify(apiService).getActiveReceiveAddress(xpub, api_code)
        verifyNoMoreInteractions(apiService)

        assertEquals(receive_address, result)
    }

    @Test
    @Throws(Exception::class)
    fun test5ActiveChangeAddressAPI() {
        val xpub = "tpubDDZWXtva1hncbfjckydHWua6Tp6gt8JPNrhQw63kGvSp6NeMQkiBbsQ3iofX1MUCR8vZzpwcKSLVdTSBtGLpDvQTnkxN5azfugVxJYG3Ytj"
        val api_code = "api_code"
        val change_address = ActiveAddress("myGTTCSyiJasZYfFXtfYx7VvV3Pc9ao5N3")

        whenever(apiService.getActiveChangeAddress(xpub, api_code))
                .thenReturn(Observable.just(change_address))

        val result : ActiveAddress = APIManager.activeChangeAddress(xpub, api_code)

        verify(apiService).getActiveChangeAddress(xpub, api_code)
        verifyNoMoreInteractions(apiService)

        assertEquals(change_address, result)
    }

    @Test
    @Throws(Exception::class)
    fun test6SpendTXOCountAPI() {
        val xpub = "tpubDDZWXtva1hncbfjckydHWua6Tp6gt8JPNrhQw63kGvSp6NeMQkiBbsQ3iofX1MUCR8vZzpwcKSLVdTSBtGLpDvQTnkxN5azfugVxJYG3Ytj"
        val api_code = "api_code"
        val count = 2

        whenever(apiService.getSpendTXOCount(xpub, api_code))
                .thenReturn(Observable.just(count))

        val result : Int = APIManager.spendTXOCount(xpub, api_code)

        verify(apiService).getSpendTXOCount(xpub, api_code)
        verifyNoMoreInteractions(apiService)

        assertEquals(count, result)
    }

    @Test
    @Throws(Exception::class)
    fun test7PushTXAPI() {
        val xpub = "tpubDDZWXtva1hncbfjckydHWua6Tp6gt8JPNrhQw63kGvSp6NeMQkiBbsQ3iofX1MUCR8vZzpwcKSLVdTSBtGLpDvQTnkxN5azfugVxJYG3Ytj"
        val api_code = "api_code"
        val sendTXResult = SendTXResult("hashtx-string", "")

        whenever(apiService.pushTX(xpub, api_code))
                .thenReturn(Observable.just(sendTXResult))

        val result : SendTXResult = APIManager.pushTX(xpub, api_code)

        verify(apiService).pushTX(xpub, api_code)
        verifyNoMoreInteractions(apiService)

        assertEquals(sendTXResult, result)
    }

    @Test
    @Throws(Exception::class)
    fun test8SendTransaction() {
        val api_code = "api_code"

        val privKeyString: String = "tprv8gHuMFPCeZmGxb5c1q9ULTqTS67XxoR9kcWWXF45v3JQaf9AshgbXw41aerfn2mgpCW8YsGeWKFpTHyZPMB5Jc6vs3ofd6zdMBKxY5iVegR"
        val pubKeyString: String = "tpubDCywVfRSnwSwr47PuUp4jsVa17dU88c4Kv7Hom6PLK6oR9PwW6WBiRfskp6HjSxgrdUvARkbNSW6cUwzgBmqSSyzeDFprTNezxXx9ASRtnd"
        val toAddress: String = "n4DB2oaHBXCyTN2ZcnWovqdKwPNXxGHXtL"
        val send_satoshi: Long = 100000000
        val utxos: MutableList<UTXO> = mutableListOf()
        utxos.add(UTXO("93833337cb17cd9ada0f69e0aeb1f62d4dc33174a71384a70303ef1f4b8665a5", 1, 110000000, "76a91408a71c9c0f7767b4738042c9fa7971944e4830a788ac", "mgJhtiYQQLhSXrEtDPNjNvmonnMXmLck5T"))
        utxos.add(UTXO("596ec6b3dae4dbc2616e8c8d82be30171d1cfa73444c88a31284a7a87b770200", 1, 4500000, "76a91463d0466dd575c6769ca91f9412397f9f273dbb8188ac", "mpcihFg5FbBJshTvmhHiSvzV7PTtbeWdX5"))

        val activeAddress = ActiveAddress("mw8QnsYdVrkH6tejhPT2YRF3F9iXSABHyN")
        whenever(apiService.getActiveChangeAddress(pubKeyString,api_code))
                .thenReturn(Observable.just(activeAddress))

        val count = 0
        whenever(apiService.getSpendTXOCount(anyString(), anyString()))
                .thenReturn(Observable.just(count))

        val presenter : SendTxOPresenter = SendTxOPresenter_Factory.create().get()
        val hashtx: String = presenter.makeTx(privKeyString, pubKeyString, toAddress,
                send_satoshi, utxos)

        val sendTXResult = SendTXResult(hashtx, "")
        whenever(apiService.pushTX(hashtx,api_code))
                .thenReturn(Observable.just(sendTXResult))

        val result : SendTXResult = presenter.pushTx(hashtx)

        assertEquals(hashtx, result.hashtx)
    }
}
