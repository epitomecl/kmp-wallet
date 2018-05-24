package com.epitomecl.kmpwallet.mvp

import io.reactivex.disposables.Disposable

/**
 * Created by elegantuniv on 2017. 6. 17..
 */

interface BasePresenter<V : BaseView> {

    fun attachView(view: V)

    fun addDisposable(disp : Disposable)

    fun detachView()

    fun getBaseView() : V

    fun isViewAttached() : Boolean
}
