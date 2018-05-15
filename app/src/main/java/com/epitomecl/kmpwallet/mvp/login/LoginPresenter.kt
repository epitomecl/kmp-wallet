package com.epitomecl.kmpwallet.mvp.login

import com.epitomecl.kmpwallet.mvp.BasePresenterImpl

class LoginPresenter : BasePresenterImpl<LoginContract.View>(),
        LoginContract.Presenter {


    override fun login(id: String, password: String) {
        //APIManager.login(id, password)
    }
}