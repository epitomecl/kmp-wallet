package com.epitomecl.kmp.core.data.payments

import com.epitomecl.kmp.core.data.EnvironmentSettings
import info.blockchain.wallet.payment.Payment
import info.blockchain.wallet.payment.SpendableUnspentOutputs
import io.reactivex.Observable
import org.bitcoinj.core.ECKey
import java.math.BigInteger
import javax.inject.Inject

class PaymentService @Inject constructor(
        private val environmentSettings : EnvironmentSettings,
        private val payment: Payment
) {


    internal fun submitPayment(
            unspendOutputBundle: SpendableUnspentOutputs,
            keys: List<ECKey>,
            toAddress: String,
            changeAddress: String,
            bigIntFee: BigInteger,
            bigIntAmount: BigInteger
    ): Observable<String> {
        return Observable.create { observableOnSubscribe ->
            val receivers = HashMap<String, BigInteger>()
            receivers[toAddress] = bigIntAmount

            val tx = payment.makeSimpleTransaction(
                    environmentSettings.bitcoinNetworkParameters,
                    unspendOutputBundle.spendableOutputs,
                    receivers,
                    bigIntFee,
                    changeAddress
            )

            payment.signSimpleTransaction(environmentSettings.bitcoinNetworkParameters, tx, keys)

            val excute = payment.publishSimpleTransaction(tx).execute()
            if(excute.isSuccessful){
                if (!observableOnSubscribe.isDisposed) {
                    observableOnSubscribe.onNext(tx.hashAsString)
                    observableOnSubscribe.onComplete()
                }
            } else {
                if (!observableOnSubscribe.isDisposed) {
                    observableOnSubscribe.onError(Throwable("""${excute.code()}: ${excute.errorBody()!!.string()}"""))
                }
            }

        }
    }
}