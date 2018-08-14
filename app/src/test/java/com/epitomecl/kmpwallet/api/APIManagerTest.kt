package com.epitomecl.kmpwallet.api

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
import com.epitomecl.kmpwallet.model.UTXO
import org.junit.Assert.assertEquals

class APIManagerTest : RxTestScheduler() {

    private val testService: TestService = mock()

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        APIManager.setTestService(testService)
    }

    @Test
    @Throws(Exception::class)
    fun loginAPITest() {
        //System.out.println("loginAPITest BEGIN")

        val id = "aaaa"
        val pw = "aaaa"
        val userVO = UserVO("user_session")

        whenever(testService.postLogin(id, pw))
                .thenReturn(Observable.just(userVO))

        val testObserver = APIManager.login(id, pw).test()

        verify(testService).postLogin(id, pw)
        verifyNoMoreInteractions(testService)
        testObserver.assertComplete()
        testObserver.assertValue(userVO)

        //System.out.println("loginAPITest END")
    }

    @Test
    @Throws(Exception::class)
    fun registAPITest() {
        val id = "aaaa"
        val pw = "aaaa"
        val userVO = UserVO("user_session")

        whenever(testService.postRegist(id, pw))
                .thenReturn(Observable.just(userVO))

        val testObserver = APIManager.regist(id, pw).test()

        verify(testService).postRegist(id, pw)
        verifyNoMoreInteractions(testService)
        testObserver.assertComplete()
        testObserver.assertValue(userVO)
    }

    @Test
    @Throws(Exception::class)
    fun balanceAPITest() {
        val xpub = "tpubDDZWXtva1hncbfjckydHWua6Tp6gt8JPNrhQw63kGvSp6NeMQkiBbsQ3iofX1MUCR8vZzpwcKSLVdTSBtGLpDvQTnkxN5azfugVxJYG3Ytj"
        val api_code = "v0.1"
        val utxolist = mutableListOf<UTXO>()//mock(mutableListOf<UTXO>()::class)
        utxolist.add(UTXO("hash-string", 0, 10000, "scriptBytes-string", "mv4b1aMUXTonnX2MaBnkxTzwd76tykykTa"))
        utxolist.add(UTXO("hash-string", 0, 10000, "scriptBytes-string", "myGTTCSyiJasZYfFXtfYx7VvV3Pc9ao5N3 "))
        utxolist.add(UTXO("hash-string", 0, 10000, "scriptBytes-string", "mrBAtbFHhvj2zHxwETwiL9Hg818xGRA97h  "))

        whenever(testService.getBalanceEx(xpub, api_code))
                .thenReturn(Observable.just(utxolist))

        val resultList : List<UTXO> = APIManager.balance(xpub, api_code)

        verify(testService).getBalanceEx(xpub, api_code)
        verifyNoMoreInteractions(testService)

        assertArrayEquals(utxolist.toTypedArray(), resultList.toTypedArray())
    }

    @Test
    @Throws(Exception::class)
    fun activeReceiveAddressAPITest() {
        val xpub = "tpubDDZWXtva1hncbfjckydHWua6Tp6gt8JPNrhQw63kGvSp6NeMQkiBbsQ3iofX1MUCR8vZzpwcKSLVdTSBtGLpDvQTnkxN5azfugVxJYG3Ytj"
        val api_code = "v0.1"
        val receive_address = ActiveAddress("mv4b1aMUXTonnX2MaBnkxTzwd76tykykTa")

        whenever(testService.getActiveReceiveAddress(xpub, api_code))
                .thenReturn(Observable.just(receive_address))

        val result : ActiveAddress = APIManager.activeReceiveAddress(xpub, api_code)

        verify(testService).getActiveReceiveAddress(xpub, api_code)
        verifyNoMoreInteractions(testService)

        assertEquals(receive_address, result)
    }

    @Test
    @Throws(Exception::class)
    fun activeChangeAddressAPITest() {
        val xpub = "tpubDDZWXtva1hncbfjckydHWua6Tp6gt8JPNrhQw63kGvSp6NeMQkiBbsQ3iofX1MUCR8vZzpwcKSLVdTSBtGLpDvQTnkxN5azfugVxJYG3Ytj"
        val api_code = "v0.1"
        val change_address = ActiveAddress("myGTTCSyiJasZYfFXtfYx7VvV3Pc9ao5N3")

        whenever(testService.getActiveChangeAddress(xpub, api_code))
                .thenReturn(Observable.just(change_address))

        val result : ActiveAddress = APIManager.activeChangeAddress(xpub, api_code)

        verify(testService).getActiveChangeAddress(xpub, api_code)
        verifyNoMoreInteractions(testService)

        assertEquals(change_address, result)
    }

    @Test
    @Throws(Exception::class)
    fun spendTXOCountAPITest() {
        val xpub = "tpubDDZWXtva1hncbfjckydHWua6Tp6gt8JPNrhQw63kGvSp6NeMQkiBbsQ3iofX1MUCR8vZzpwcKSLVdTSBtGLpDvQTnkxN5azfugVxJYG3Ytj"
        val api_code = "v0.1"
        val count = 2

        whenever(testService.getSpendTXOCount(xpub, api_code))
                .thenReturn(Observable.just(count))

        val result : Int = APIManager.spendTXOCount(xpub, api_code)

        verify(testService).getSpendTXOCount(xpub, api_code)
        verifyNoMoreInteractions(testService)

        assertEquals(count, result)
    }

    @Test
    @Throws(Exception::class)
    fun pushTXAPITest() {
        val xpub = "tpubDDZWXtva1hncbfjckydHWua6Tp6gt8JPNrhQw63kGvSp6NeMQkiBbsQ3iofX1MUCR8vZzpwcKSLVdTSBtGLpDvQTnkxN5azfugVxJYG3Ytj"
        val api_code = "v0.1"
        val sendTXResult = SendTXResult("hashtx-string")

        whenever(testService.pushTX(xpub, api_code))
                .thenReturn(Observable.just(sendTXResult))

        val result : SendTXResult = APIManager.pushTX(xpub, api_code)

        verify(testService).pushTX(xpub, api_code)
        verifyNoMoreInteractions(testService)

        assertEquals(sendTXResult, result)
    }
}
