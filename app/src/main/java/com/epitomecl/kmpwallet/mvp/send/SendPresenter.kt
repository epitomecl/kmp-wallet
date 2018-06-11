package com.epitomecl.kmpwallet.mvp.send

import android.util.Log
import com.epitomecl.kmpwallet.data.Constants
import com.epitomecl.kmpwallet.data.KmpDataManager
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import com.epitomecl.kmpwallet.mvp.base.RxCallbackWrapper
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SendPresenter : BasePresenterImpl<SendContract.View>,
        SendContract.Presenter {

    @Inject
    internal lateinit var mDataManager: KmpDataManager

    @Inject
    constructor()

    override fun send(from: String, to: String, amount: String, fee: Long) {
        var amountAsLong = amount.toLongOrNull()
        if(amountAsLong==null || amountAsLong < 0){
            mView?.showError("Invalid amount")
            return
        }

        mView?.showLoading()

        addDisposable(mDataManager!!
                .send(from, to, amountAsLong, fee)
                .doOnNext({ jsonObject -> Log.d(Constants.TAG, jsonObject.toString()) })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : RxCallbackWrapper<JsonObject>(this) {
                    override fun onNext(result: JsonObject){
                        if(!isViewAttached()) return
                        getBaseView().hideLoading()
                        Log.d(Constants.TAG, result.toString())
                    }
                })
            )
    }

//    fun submitBitcoinTransaction() : Observable<JsonObject> {
//        mView?.showLoading()
//
//
//    }
}

