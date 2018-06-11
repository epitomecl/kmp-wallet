package com.epitomecl.kmp.core.data.payments

import info.blockchain.wallet.payment.SpendableUnspentOutputs
import io.reactivex.Observable
import org.bitcoinj.core.ECKey
import java.math.BigInteger
import javax.inject.Inject
import com.epitomecl.kmp.core.util.rxjava.RxBus
import com.epitomecl.kmp.core.util.rxjava.RxPinning
import com.epitomecl.kmp.core.util.rxjava.applySchedulers

class SendDataManager @Inject constructor(
        private val paymentService: PaymentService,
        rxBus: RxBus
) {

    private val rxPinning: RxPinning = RxPinning(rxBus)

    /**
     * Submits a Bitcoin payment to a specified BTC address and returns the transaction hash if
     * successful
     *
     * @param unspentOutputBundle UTXO object
     * @param keys                A List of elliptic curve keys
     * @param toAddress           The address to send the funds to
     * @param changeAddress       A change address
     * @param bigIntFee           The specified fee amount
     * @param bigIntAmount        The actual transaction amount
     * @return An [Observable] wrapping a [String] where the String is the transaction hash
     */
    fun submitBtcPayment(
            unspentOutputBundle: SpendableUnspentOutputs,
            keys: List<ECKey>,
            toAddress: String,
            changeAddress: String,
            bigIntFee: BigInteger,
            bigIntAmount: BigInteger
    ): Observable<String> {

        return rxPinning.call<String> {
            paymentService.submitPayment(
                    unspentOutputBundle,
                    keys,
                    toAddress,
                    changeAddress,
                    bigIntFee,
                    bigIntAmount
            )
        }.applySchedulers()
    }
}