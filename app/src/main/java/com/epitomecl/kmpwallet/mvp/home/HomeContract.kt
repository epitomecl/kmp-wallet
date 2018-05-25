package com.epitomecl.kmpwallet.mvp.home

import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object HomeContract {
    interface View : BaseView {
        fun showTest()
    }

    interface Presenter : BasePresenter<View> {
        fun loadTest(param: Number)
    }
}