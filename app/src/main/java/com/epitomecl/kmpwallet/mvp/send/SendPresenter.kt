package com.epitomecl.kmpwallet.mvp.send

import android.util.Log
import com.epitomecl.kmp.core.util.rpc.BitcoinRPCClient
import com.epitomecl.kmp.core.util.rpc.KmpRPCClient
import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmpwallet.data.Constants
import com.epitomecl.kmpwallet.data.payments.SendDataManager
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import com.epitomecl.kmpwallet.mvp.base.RxCallbackWrapper
import info.blockchain.wallet.payload.data.Account
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.bitcoinj.core.ECKey
import piuk.blockchain.androidcore.data.payload.PayloadDataManager
import piuk.blockchain.androidcore.utils.helperfunctions.unsafeLazy
import timber.log.Timber
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient
import javax.inject.Inject
import kotlin.concurrent.thread

class SendPresenter @Inject constructor(
        private val payloadDataManager: PayloadDataManager,
        private val sendDataManager: SendDataManager
): BasePresenterImpl<SendContract.View>(),
        SendContract.Presenter {

    private val pendingTransaction by unsafeLazy { PendingTransaction() }

//    private val verifiedSecondPassword: String? = null


//    override fun send(from: String, to: String, amount: String, fee: Long) {
//        var amountAsLong = amount.toLongOrNull()
//        if(amountAsLong==null || amountAsLong < 0){
//            mView?.showError("Invalid amount")
//            return
//        }
//
//        mView?.showLoading()
//
//        addDisposable(mDataManager!!
//                .send(from, to, amountAsLong, fee)
//                .doOnNext({ jsonObject -> Log.d(Constants.TAG, jsonObject.toString()) })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : RxCallbackWrapper<JsonObject>(this) {
//                    override fun onNext(result: JsonObject){
//                        if(!isViewAttached()) return
//                        getBaseView().hideLoading()
//                        Log.d(Constants.TAG, result.toString())
//                    }
//                })
//            )
//    }

    fun getTransactionViaRpc(txid : String) {
        addDisposable(
                Observable.fromCallable { KmpRPCClient.get(CryptoType.BITCOIN)?.getTransaction(txid) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : RxCallbackWrapper<BitcoindRpcClient.RawTransaction>(this){
                            override fun onNext(t: BitcoindRpcClient.RawTransaction) {
                                Log.d(Constants.TAG, t.toString());
                                mView?.onSendSuccess(t.toString())
                            }
                        })
        )

    }

    fun sendFromBTCViaRpc(fromAccount : String, toAddress: String, amount: Double) {
        addDisposable(
                Observable.fromCallable { KmpRPCClient.get(CryptoType.BITCOIN)?.sendFrom(fromAccount, toAddress, amount) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : RxCallbackWrapper<String>(this){
                            override fun onNext(t: String) {
                                Log.d(Constants.TAG, t);
                                mView?.onSendSuccess(t)
                            }
                        })
        )
    }


    fun submitBitcoinTransaction() {
        mView?.showLoading()

        getBtcChangeAddress()
                .doOnSubscribe{mCompositeDisposable!!.add(it)}
                .doOnError {
                    mView?.hideLoading()
                }
                .map { pendingTransaction.changeAddress = it }
                .flatMap { getBtcKeys() }
                .flatMap {
                    sendDataManager.submitBtcPayment(
                            pendingTransaction.unspentOutputBundle,
                            it,
                            pendingTransaction.receivingAddress,
                            pendingTransaction.changeAddress,
                            pendingTransaction.bigIntFee,
                            pendingTransaction.bigIntAmount
                    )
                }
                .subscribe({hash ->
                    //emit event logs to server
                    Log.d(Constants.TAG, "complete to submit transaction")
                    mView?.hideLoading()

                    incrementBtcReceiveAddress()
                    handleSuccessfulPayment(hash, "BTC")

                }) {
                    Timber.e(it)
                    mView?.hideLoading()
                }
    }

    private fun getBtcChangeAddress(): Observable<String> {
        val account = pendingTransaction.sendingObject.accountObject as Account
        return payloadDataManager.getNextChangeAddress(account)
    }

    private fun incrementBtcReceiveAddress() {
        val account = pendingTransaction.sendingObject.accountObject as Account
        payloadDataManager.incrementChangeAddress(account)
        payloadDataManager.incrementReceiveAddress(account)
        updateInternalBtcBalances()
    }

    private fun getBtcKeys(): Observable<List<ECKey>> {
        //only HD allowable.
        val account = pendingTransaction.sendingObject.accountObject as Account
//        if(payloadDataManager.isDoubleEncrypted){
//            payloadDataManager.decryptHDWallet(verifiedSecondPassword)
//        }
        return Observable.just(
                payloadDataManager.getHDKeysForSigning(
                        account,
                        pendingTransaction.unspentOutputBundle
                )
        )
    }

    private fun updateInternalBtcBalances() {
        try {
            val totalSent = pendingTransaction.bigIntAmount.add(pendingTransaction.bigIntFee)
            val account = pendingTransaction.sendingObject.accountObject as Account
            payloadDataManager.subtractAmountFromAddressBalance(
                    account.xpub,
                    totalSent.toLong()
            )
        }catch (e: Exception){
            Timber.e(e)
        }
    }

    private fun handleSuccessfulPayment(hash: String, currency: String) : String {
        mView?.showTransactionSuccess(hash, pendingTransaction.bigIntAmount.toLong(), currency)

        pendingTransaction.clear()
        return hash
    }
}

