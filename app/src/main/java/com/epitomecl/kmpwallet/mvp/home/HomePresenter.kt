package com.epitomecl.kmpwallet.mvp.home

import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl

class HomePresenter : BasePresenterImpl<HomeContract.View>(),
        HomeContract.Presenter {
    companion object {
        private const val TEST_NAME = "test1"
    }

    override fun loadTest(param: Number) {
        APIManager.loadTest(param)
    }
}