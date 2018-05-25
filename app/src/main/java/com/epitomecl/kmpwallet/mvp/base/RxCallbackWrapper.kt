package com.epitomecl.kmpwallet.mvp.base

import io.reactivex.observers.DisposableObserver
import java.lang.ref.WeakReference


open class RxCallbackWrapper<T>(impl : BasePresenterImpl<out BaseView>) : DisposableObserver<T>() {

    private val mPresenterRef: WeakReference<BasePresenterImpl<out BaseView>>

    init {
        mPresenterRef = WeakReference<BasePresenterImpl<out BaseView>>(impl)
    }


    override fun onNext(t: T) {
        //
    }

    override fun onError(e: Throwable) {
        val presenter = mPresenterRef.get()
        if(presenter==null || !presenter.isViewAttached()){
            return
        }

        presenter.getBaseView()

        presenter.getBaseView().showError(e.message)
    }

    override fun onComplete() {
        val presenter = mPresenterRef.get()
        if(presenter==null || !presenter.isViewAttached()){
            return
        }
        presenter.getBaseView().hideLoading()
    }

}
