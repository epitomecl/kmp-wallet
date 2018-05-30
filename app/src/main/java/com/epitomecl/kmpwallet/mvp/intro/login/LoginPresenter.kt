package com.epitomecl.kmpwallet.mvp.intro.login

import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl

class LoginPresenter : BasePresenterImpl<LoginContract.View>(),
        LoginContract.Presenter {

    override fun loginUser(id : String, pw : String) : Boolean {
        var loginId = AppData.getLoginId()
        var loginPw = AppData.getLoginPw()

        if((loginId == id) && (loginPw == pw)) {
            return true
        }
        return false
    }
}