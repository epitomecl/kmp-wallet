package com.epitomecl.kmpwallet.data.payments

import info.blockchain.wallet.payment.SpendableUnspentOutputs
import io.reactivex.Observable
import org.bitcoinj.core.ECKey
import piuk.blockchain.androidcore.data.rxjava.RxBus
import piuk.blockchain.androidcore.data.rxjava.RxPinning
import piuk.blockchain.androidcore.injection.PresenterScope
import piuk.blockchain.androidcore.utils.extensions.applySchedulers
import java.math.BigInteger
import javax.inject.Inject


@PresenterScope
class SendDataManager @Inject constructor(
        private val paymentService: PaymentService,
        rxBus: RxBus
) {

    private val rxPinning: RxPinning = RxPinning(rxBus)

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