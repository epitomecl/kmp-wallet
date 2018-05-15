package com.epitomecl.kmpwallet.mvp

import io.reactivex.disposables.CompositeDisposable

open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {
    protected var mView: V? = null
    protected var compositeDisposable: CompositeDisposable? = null

    constructor() {
        compositeDisposable = CompositeDisposable()
    }

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        compositeDisposable!!.clear()

        mView = null
    }
}