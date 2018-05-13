package com.epitomecl.kmpwallet.mvp.home

import com.epitomecl.kmpwallet.mvp.BasePresenter
import com.epitomecl.kmpwallet.mvp.BaseView

object HomeContract {
    interface View : BaseView {
        fun showTest()
    }

    interface Presenter : BasePresenter<View> {
        fun loadTest(param: Number)
    }
}