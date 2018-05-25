package com.epitomecl.kmpwallet.mvp.intro

import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl

class IntroPresenter : BasePresenterImpl<IntroContract.View>(),
        IntroContract.Presenter {


    override fun login(id: String, password: String) {
        //APIManager.intro(id, password)
    }
}