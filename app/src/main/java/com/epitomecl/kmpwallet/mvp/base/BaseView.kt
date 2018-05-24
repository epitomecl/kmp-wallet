package com.epitomecl.kmpwallet.mvp

import android.content.Context

/**
 * Created by elegantuniv on 2017. 6. 17..
 */

interface BaseView {
    fun getContext(): Context

    fun showError(error: String?)
    fun showMessage(message: String)

    fun showLoading()
    fun hideLoading()

    fun onFailureRequest(msg: String)

}
