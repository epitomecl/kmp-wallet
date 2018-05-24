package com.epitomecl.kmpwallet.mvp.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {
    protected var mView: V? = null
    protected var mCompositeDisposable: CompositeDisposable? = null

    constructor() {
        mCompositeDisposable = CompositeDisposable()
    }

    override fun attachView(view: V) {
        mView = view
    }

    override fun addDisposable(disp: Disposable) {
        if(mCompositeDisposable == null){
            mCompositeDisposable = CompositeDisposable()
        }

        if(!mCompositeDisposable!!.isDisposed()){
            mCompositeDisposable!!.add(disp)
        }
    }

    override fun detachView() {
        if(mView!=null){
            mView!!.hideLoading()
        }

        mView = null

        if(mCompositeDisposable!=null){
            mCompositeDisposable!!.clear()
        }
    }

    override fun getBaseView(): V {
        return mView!!
    }

    override fun isViewAttached(): Boolean {
        return mView != null
    }
}
