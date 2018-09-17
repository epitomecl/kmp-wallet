package com.epitomecl.kmpwallet.mvp.base

import android.content.Context

/**
 * Created by elegantuniv on 2017. 6. 17..
 */

interface BaseView {

    fun showError(error: String?)
    fun showError(resId: Int)
    fun showMessage(message: String)

    fun showLoading()
    fun hideLoading()

    fun onFailureRequest(msg: String)
}
