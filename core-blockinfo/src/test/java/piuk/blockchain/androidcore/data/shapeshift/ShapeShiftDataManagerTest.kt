package piuk.blockchain.androidcore.data.shapeshift

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import info.blockchain.wallet.shapeshift.ShapeShiftApi
import info.blockchain.wallet.shapeshift.ShapeShiftTrades
import info.blockchain.wallet.shapeshift.data.*
import io.reactivex.Completable
import io.reactivex.Observable
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should not contain`
import org.json.JSONException
import org.junit.Before
import org.junit.Test
import piuk.blockchain.androidcore.RxTest
import piuk.blockchain.androidcore.data.metadata.MetadataManager
import piuk.blockchain.androidcore.data.rxjava.RxBus
import piuk.blockchain.androidcore.data.shapeshift.datastore.ShapeShiftDataStore
import piuk.blockchain.androidcore.data.shapeshift.models.CoinPairings
import piuk.blockchain.androidcore.utils.Either
import piuk.blockchain.androidcore.utils.Optional

@Suppress("IllegalIdentifier")
class ShapeShiftDataManagerTest : RxTest() {

    private lateinit var subject: ShapeShiftDataManager
    private val shapeShiftApi: ShapeShiftApi = mock()
    private val shapeShiftDataStore: ShapeShiftDataStore = mock()
    private val metadataManager: MetadataManager = mock()
    private val rxBus: RxBus =
            RxBus()

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        subject = ShapeShiftDataManager(
                shapeShiftApi,
                shapeShiftDataStore,
                metadataManager,
                rxBus
        )
    }

    @Test
    @Throws(Exception::class)
    fun initShapeshiftTradeData() {
        // Arrange
        // TODO: This isn't testable currently
        // Act

        // Assert

    }

    @Test
    @Throws(Exception::class)
    fun `getState initialized null`() {
        // Arrange
        val tradeData: ShapeShiftTrades = mock()
        whenever(shapeShiftDataStore.tradeData).thenReturn(tradeData)
        whenever(tradeData.usState).thenReturn(null)
        // Act
        val testObserver = subject.getState().test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(Optional.None)
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test
    @Throws(Exception::class)
    fun `getState initialized with value`() {
        // Arrange
        val tradeData: ShapeShiftTrades = mock()
        val state = State("STATE", "STATE")
        whenever(shapeShiftDataStore.tradeData).thenReturn(tradeData)
        whenever(tradeData.usState).thenReturn(state)
        // Act
        val testObserver = subject.getState().test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        (testObserver.values()[0] as Optional.Some<State>).element `should be` state
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test(expected = IllegalStateException::class)
    @Throws(Exception::class)
    fun `getState uninitialized`() {
        // Arrange
        whenever(shapeShiftDataStore.tradeData).thenReturn(null)
        // Act
        val testObserver = subject.getState().test()
        // Assert
        testObserver.assertNotComplete()
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test
    @Throws(Exception::class)
    fun `setState initialized`() {
        // Arrange
        val tradeData: ShapeShiftTrades = mock()
        val state = State("STATE", "STATE")
        whenever(shapeShiftDataStore.tradeData).thenReturn(tradeData)
        whenever(shapeShiftDataStore.tradeData!!.toJson()).thenReturn("{}")
        whenever(metadataManager.saveToMetadata(any(), any())).thenReturn(Completable.complete())
        // Act
        val testObserver = subject.setState(state).test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        verify(shapeShiftDataStore, atLeastOnce()).tradeData
        verify(metadataManager).saveToMetadata(tradeData.toJson(), ShapeShiftTrades.METADATA_TYPE_EXTERNAL)
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test(expected = IllegalStateException::class)
    @Throws(Exception::class)
    fun `setState uninitialized`() {
        // Arrange
        val state = State("STATE", "STATE")
        whenever(shapeShiftDataStore.tradeData).thenReturn(null)
        // Act
        val testObserver = subject.setState(state).test()
        // Assert
        testObserver.assertNotComplete()
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test
    @Throws(Exception::class)
    fun `getTradesList initialized`() {
        // Arrange
        val tradeData: ShapeShiftTrades = mock()
        val list = emptyList<Trade>()
        whenever(shapeShiftDataStore.tradeData).thenReturn(tradeData)
        whenever(tradeData.trades).thenReturn(list)
        // Act
        val testObserver = subject.getTradesList().test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(list)
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test(expected = IllegalStateException::class)
    @Throws(Exception::class)
    fun `getTradesList uninitialized`() {
        // Arrange
        whenever(shapeShiftDataStore.tradeData).thenReturn(null)
        // Act
        val testObserver = subject.getTradesList().test()
        // Assert
        testObserver.assertNotComplete()
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test(expected = IllegalStateException::class)
    @Throws(Exception::class)
    fun `findTrade uninitialized`() {
        // Arrange
        val depositAddress = "DEPOSIT_ADDRESS"
        // Act
        val testObserver = subject.findTrade(depositAddress).test()
        // Assert
        testObserver.assertNotComplete()
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test
    @Throws(Exception::class)
    fun `findTrade not found`() {
        // Arrange
        val depositAddress = "DEPOSIT_ADDRESS"
        val tradeData: ShapeShiftTrades = mock()
        val list = emptyList<Trade>()
        whenever(shapeShiftDataStore.tradeData).thenReturn(tradeData)
        whenever(tradeData.trades).thenReturn(list)
        // Act
        val testObserver = subject.findTrade(depositAddress).test()
        // Assert
        testObserver.assertNotComplete()
        testObserver.assertError(Throwable::class.java)
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test
    @Throws(Exception::class)
    fun `findTrade found`() {
        // Arrange
        val depositAddress = "DEPOSIT_ADDRESS"
        val tradeData: ShapeShiftTrades = mock()
        val trade = Trade().apply { quote = Quote().apply { deposit = depositAddress } }
        val list = listOf(trade)
        whenever(shapeShiftDataStore.tradeData).thenReturn(tradeData)
        whenever(tradeData.trades).thenReturn(list)
        // Act
        val testObserver = subject.findTrade(depositAddress).test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(trade)
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test(expected = IllegalStateException::class)
    @Throws(Exception::class)
    fun `addTradeToList uninitialized`() {
        // Arrange
        val trade = Trade()
        // Act
        val testObserver = subject.addTradeToList(trade).test()
        // Assert
        testObserver.assertNotComplete()
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test
    @Throws(Exception::class)
    fun `addTradeToList initialized`() {
        // Arrange
        val trade = Trade()
        val list = mutableListOf<Trade>()
        val tradeData: ShapeShiftTrades = mock()
        whenever(shapeShiftDataStore.tradeData).thenReturn(tradeData)
        whenever(tradeData.trades).thenReturn(list)
        whenever(shapeShiftDataStore.tradeData!!.toJson()).thenReturn("{}")
        whenever(metadataManager.saveToMetadata(any(), any())).thenReturn(Completable.complete())
        // Act
        val testObserver = subject.addTradeToList(trade).test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        verify(shapeShiftDataStore, atLeastOnce()).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
        tradeData.trades.size `should equal to` 1
    }

    @Test(expected = IllegalStateException::class)
    @Throws(Exception::class)
    fun `clearAllTrades uninitialized`() {
        // Arrange

        // Act
        val testObserver = subject.clearAllTrades().test()
        // Assert
        testObserver.assertNotComplete()
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test
    @Throws(Exception::class)
    fun `clearAllTrades initialized`() {
        // Arrange
        val trade = Trade()
        val list = mutableListOf(trade)
        val tradeData: ShapeShiftTrades = mock()
        whenever(shapeShiftDataStore.tradeData).thenReturn(tradeData)
        whenever(tradeData.trades).thenReturn(list)
        whenever(shapeShiftDataStore.tradeData!!.toJson()).thenReturn("{}")
        whenever(metadataManager.saveToMetadata(any(), any())).thenReturn(Completable.complete())
        // Act
        val testObserver = subject.clearAllTrades().test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        verify(shapeShiftDataStore, atLeastOnce()).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
        tradeData.trades.size `should equal to` 0
    }

    @Test(expected = IllegalStateException::class)
    @Throws(Exception::class)
    fun `updateTrade uninitialized`() {
        // Arrange
        val trade = Trade()
        // Act
        val testObserver = subject.updateTrade(trade).test()
        // Assert
        testObserver.assertNotComplete()
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
    }

    @Test
    @Throws(Exception::class)
    fun `updateTrade found, save successful`() {
        // Arrange
        val orderId = "ORDER_ID"
        val trade = Trade().apply { quote = Quote().apply { this.orderId = orderId } }
        val updatedTrade = Trade().apply { quote = Quote().apply { this.orderId = orderId } }
        val list = mutableListOf(trade)
        val tradeData: ShapeShiftTrades = mock()
        whenever(shapeShiftDataStore.tradeData).thenReturn(tradeData)
        whenever(tradeData.trades).thenReturn(list)
        whenever(shapeShiftDataStore.tradeData!!.toJson()).thenReturn("{}")
        whenever(metadataManager.saveToMetadata(any(), any())).thenReturn(Completable.complete())
        // Act
        val testObserver = subject.updateTrade(updatedTrade).test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        verify(shapeShiftDataStore, atLeastOnce()).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
        tradeData.trades.size `should equal to` 1
        tradeData.trades `should contain` updatedTrade
        tradeData.trades `should not contain` trade
    }

    @Test
    @Throws(Exception::class)
    fun `updateTrade found, save failed`() {
        // Arrange
        val orderId = "ORDER_ID"
        val trade = Trade().apply { quote = Quote().apply { this.orderId = orderId } }
        val updatedTrade = Trade().apply { quote = Quote().apply { this.orderId = orderId } }
        val list = mutableListOf(trade)
        val tradeData: ShapeShiftTrades = mock()
        whenever(shapeShiftDataStore.tradeData).thenReturn(tradeData)
        whenever(tradeData.trades).thenReturn(list)
        whenever(shapeShiftDataStore.tradeData!!.toJson()).thenReturn("{}")
        whenever(metadataManager.saveToMetadata(any(), any())).thenThrow(JSONException::class.java)
        // Act
        val testObserver = subject.updateTrade(updatedTrade).test()
        // Assert
        testObserver.assertNotComplete()
        testObserver.assertError(JSONException::class.java)
        verify(shapeShiftDataStore, atLeastOnce()).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
        tradeData.trades.size `should equal to` 1
        tradeData.trades `should contain` trade
        tradeData.trades `should not contain` updatedTrade
    }

    @Test
    @Throws(Exception::class)
    fun `updateTrade not found`() {
        // Arrange
        val orderId = "ORDER_ID"
        val trade = Trade().apply { quote = Quote().apply { this.orderId = orderId } }
        val updatedTrade = Trade().apply { quote = Quote().apply { this.orderId = "" } }
        val list = mutableListOf(trade)
        val tradeData: ShapeShiftTrades = mock()
        whenever(shapeShiftDataStore.tradeData).thenReturn(tradeData)
        whenever(tradeData.trades).thenReturn(list)
        // Act
        val testObserver = subject.updateTrade(updatedTrade).test()
        // Assert
        testObserver.assertNotComplete()
        testObserver.assertError(Throwable::class.java)
        verify(shapeShiftDataStore).tradeData
        verifyNoMoreInteractions(shapeShiftDataStore)
        tradeData.trades.size `should equal to` 1
        tradeData.trades `should contain` trade
        tradeData.trades `should not contain` updatedTrade
    }

    @Test
    @Throws(Exception::class)
    fun `getTradeStatus success`() {
        // Arrange
        val depositAddress = "DEPOSIT_ADDRESS"
        val response: TradeStatusResponse = mock()
        whenever(shapeShiftApi.getTradeStatus(depositAddress)).thenReturn(Observable.just(response))
        // Act
        val testObserver = subject.getTradeStatus(depositAddress).test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(response)
        verify(shapeShiftApi).getTradeStatus(depositAddress)
        verifyNoMoreInteractions(shapeShiftApi)
    }

    @Test
    @Throws(Exception::class)
    fun `getTradeStatus failed`() {
        // Arrange
        val depositAddress = "DEPOSIT_ADDRESS"
        val response: TradeStatusResponse = mock()
        whenever(response.error).thenReturn("ERROR_STRING")
        whenever(shapeShiftApi.getTradeStatus(depositAddress)).thenReturn(Observable.just(response))
        // Act
        val testObserver = subject.getTradeStatus(depositAddress).test()
        // Assert
        testObserver.assertNotComplete()
        testObserver.assertError(Throwable::class.java)
        verify(shapeShiftApi).getTradeStatus(depositAddress)
        verifyNoMoreInteractions(shapeShiftApi)
    }

    @Test
    @Throws(Exception::class)
    fun getRate() {
        // Arrange
        val coinPairing = CoinPairings.ETH_TO_BTC
        val marketInfo: MarketInfo = mock()
        whenever(shapeShiftApi.getRate(coinPairing.pairCode)).thenReturn(Observable.just(marketInfo))
        // Act
        val testObserver = subject.getRate(coinPairing).test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(marketInfo)
        verify(shapeShiftApi).getRate(coinPairing.pairCode)
        verifyNoMoreInteractions(shapeShiftApi)
    }

    @Test
    @Throws(Exception::class)
    fun `getQuote returns valid quote`() {
        // Arrange
        val quoteRequest: QuoteRequest = mock()
        val quote: Quote = mock()
        val responseWrapper: SendAmountResponseWrapper = mock()
        whenever(responseWrapper.wrapper).thenReturn(quote)
        whenever(shapeShiftApi.getQuote(quoteRequest)).thenReturn(Observable.just(responseWrapper))
        // Act
        val testObserver = subject.getQuote(quoteRequest).test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(Either.Right(quote))
        verify(shapeShiftApi).getQuote(quoteRequest)
        verifyNoMoreInteractions(shapeShiftApi)
    }

    @Test
    @Throws(Exception::class)
    fun `getQuote returns error string`() {
        // Arrange
        val quoteRequest: QuoteRequest = mock()
        val error = "ERROR"
        val responseWrapper: SendAmountResponseWrapper = mock()
        whenever(responseWrapper.error).thenReturn(error)
        whenever(shapeShiftApi.getQuote(quoteRequest)).thenReturn(Observable.just(responseWrapper))
        // Act
        val testObserver = subject.getQuote(quoteRequest).test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(Either.Left(error))
        verify(shapeShiftApi).getQuote(quoteRequest)
        verifyNoMoreInteractions(shapeShiftApi)
    }

    @Test
    @Throws(Exception::class)
    fun `getApproximateQuote returns valid quote`() {
        // Arrange
        val quoteRequest: QuoteRequest = mock()
        val quote: Quote = mock()
        val responseWrapper: QuoteResponseWrapper = mock()
        whenever(responseWrapper.wrapper).thenReturn(quote)
        whenever(shapeShiftApi.getApproximateQuote(quoteRequest))
                .thenReturn(Observable.just(responseWrapper))
        // Act
        val testObserver = subject.getApproximateQuote(quoteRequest).test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(Either.Right(quote))
        verify(shapeShiftApi).getApproximateQuote(quoteRequest)
        verifyNoMoreInteractions(shapeShiftApi)
    }

    @Test
    @Throws(Exception::class)
    fun `getApproximateQuote returns error string`() {
        // Arrange
        val quoteRequest: QuoteRequest = mock()
        val error = "ERROR"
        val responseWrapper: QuoteResponseWrapper = mock()
        whenever(responseWrapper.error).thenReturn(error)
        whenever(shapeShiftApi.getApproximateQuote(quoteRequest))
                .thenReturn(Observable.just(responseWrapper))
        // Act
        val testObserver = subject.getApproximateQuote(quoteRequest).test()
        // Assert
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(Either.Left(error))
        verify(shapeShiftApi).getApproximateQuote(quoteRequest)
        verifyNoMoreInteractions(shapeShiftApi)

    }

}