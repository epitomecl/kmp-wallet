package com.epitomecl.kmpwallet.mvp.account

import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import info.blockchain.wallet.exceptions.DecryptionException
import info.blockchain.wallet.exceptions.PayloadException
import piuk.blockchain.androidcore.data.payload.PayloadDataManager
import timber.log.Timber
import javax.inject.Inject

class AccountPresenter @Inject constructor(
    private val payloadDataManager: PayloadDataManager
): BasePresenterImpl<AccountContract.View>(),
        AccountContract.Presenter {

    /* refer from block.info */
    internal fun createNewAccount(accountLabel : String) {
        //check if the label already used.
        ///
        payloadDataManager.createNewAccount(accountLabel, null)
                .doOnSubscribe { mView?.showLoading() }
                .doAfterTerminate { mView?.hideLoading() }
                .doOnError { Timber.e(it) }
                .subscribe(
                        {
                            mView?.onSuccessAccountCreated()
                        },
                        { throwable ->
                            when (throwable) {
                                is PayloadException -> mView?.onError("Save unsuccessful")
                                else -> mView?.onError("Unexpected error, please try again later.")
                            }
                        }
                )
    }
}