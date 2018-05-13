package com.epitomecl.kmpwallet.mvp

/**
 * Created by elegantuniv on 2017. 6. 17..
 */

interface BasePresenter<in V : BaseView> {

    fun attachView(view: V)

    fun detachView()

}
