package piuk.blockchain.androidcore.data.exchangerate

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.amshove.kluent.mock
import org.junit.Before
import org.web3j.utils.Convert
import piuk.blockchain.androidcore.data.exchangerate.datastore.ExchangeRateDataStore
import piuk.blockchain.androidcore.data.rxjava.RxBus
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class ExchangeRateDataManagerTest {

    private lateinit var subject: ExchangeRateDataManager
    private val exchangeRateDataStore: ExchangeRateDataStore = mock()
    private val rxBus: RxBus = mock()

    @Before
    fun setUp() {
        subject = ExchangeRateDataManager(
                exchangeRateDataStore,
                rxBus
        )
    }

    @Test
    fun getFiatFromBtc() {

        // Arrange
        val exchangeRate = 5000.0
        val satoshis = 10L
        whenever(exchangeRateDataStore.getLastBtcPrice("USD")).thenReturn(exchangeRate)

        // Act
        val result = subject.getFiatFromBtc(BigDecimal.valueOf(satoshis), "USD")

        // Assert
        assertEquals(BigDecimal.valueOf(exchangeRate).multiply(BigDecimal.valueOf(satoshis)), result)
    }

    @Test
    fun getFiatFromEth() {

        // Arrange
        val exchangeRate = 5000.0
        val satoshis = 10L
        whenever(exchangeRateDataStore.getLastEthPrice("USD")).thenReturn(exchangeRate)

        // Act
        val result = subject.getFiatFromEth(BigDecimal.valueOf(satoshis), "USD")

        // Assert
        assertEquals(BigDecimal.valueOf(exchangeRate).multiply(BigDecimal.valueOf(satoshis)), result)
    }

    @Test
    fun getFiatFromBch() {

        // Arrange
        val exchangeRate = 5000.0
        val satoshis = 10L
        whenever(exchangeRateDataStore.getLastBchPrice("USD")).thenReturn(exchangeRate)

        // Act
        val result = subject.getFiatFromBch(BigDecimal.valueOf(satoshis), "USD")

        // Assert
        assertEquals(BigDecimal.valueOf(exchangeRate).multiply(BigDecimal.valueOf(satoshis)), result)
    }

    @Test
    fun getBtcFromFiat() {

        // Arrange
        whenever(exchangeRateDataStore.getLastBtcPrice("USD")).thenReturn(8100.37)

        // Act
        val result = subject.getBtcFromFiat(BigDecimal.valueOf(4050.18), "USD")

        // Assert
        assertEquals(BigDecimal.valueOf(0.49999938), result)
    }

    @Test
    fun getBchFromFiat() {

        // Arrange
        whenever(exchangeRateDataStore.getLastBchPrice("USD")).thenReturn(8100.37)

        // Act
        val result = subject.getBchFromFiat(BigDecimal.valueOf(4050.18), "USD")

        // Assert
        assertEquals(BigDecimal.valueOf(0.49999938), result)
    }

    @Test
    fun getEthFromFiat() {

        // Arrange
        whenever(exchangeRateDataStore.getLastEthPrice("USD")).thenReturn(8100.37)

        // Act
        val result = subject.getEthFromFiat(BigDecimal.valueOf(4050.18), "USD")

        // Assert
        assertEquals(BigDecimal.valueOf(0.49999938), result)
    }

    @Test
    fun getBtcHistoricPrice() {

        // Arrange
        whenever(exchangeRateDataStore.getBtcHistoricPrice(any(), any()))
                .thenReturn(Observable.just(8100.37))

        // Act
        // Assert
        subject.getBtcHistoricPrice((1e8.toLong() / 2), "", 0L).test()
                .assertValue { result -> result.compareTo(BigDecimal.valueOf(4050.185)) == 0 }
        subject.getBtcHistoricPrice((1e8.toLong() / 3), "", 0L).test()
                .assertValue { result -> result.compareTo(BigDecimal.valueOf(2700.1233063321)) == 0 }
    }

    @Test
    fun getEthHistoricPrice() {

        // Arrange
        whenever(exchangeRateDataStore.getEthHistoricPrice(any(), any()))
                .thenReturn(Observable.just(553.37))

        // Act
        // Assert
        val result1 = subject.getEthHistoricPrice(Convert.toWei("1.0", Convert.Unit.ETHER).toBigInteger(), "", 0L)
                .test().values()[0]
        val result2 = subject.getEthHistoricPrice(Convert.toWei("0.5", Convert.Unit.ETHER).toBigInteger(), "", 0L)
                .test().values()[0]

        assertEquals("553.3700000000", result1.toString())
        assertEquals("276.6850000000", result2.toString())
    }

    @Test
    fun getBchHistoricPrice() {

        // Arrange
        whenever(exchangeRateDataStore.getBchHistoricPrice(any(), any()))
                .thenReturn(Observable.just(8100.37))

        // Act
        // Assert
        subject.getBchHistoricPrice((1e8.toLong() / 2), "", 0L).test()
                .assertValue { result -> result.compareTo(BigDecimal.valueOf(4050.185)) == 0 }
        subject.getBchHistoricPrice((1e8.toLong() / 3), "", 0L).test()
                .assertValue { result -> result.compareTo(BigDecimal.valueOf(2700.1233063321)) == 0 }
    }
}
