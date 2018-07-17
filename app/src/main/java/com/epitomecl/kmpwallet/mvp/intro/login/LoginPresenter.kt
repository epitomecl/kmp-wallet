package com.epitomecl.kmpwallet.mvp.intro.login

import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.model.UserVO
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import io.reactivex.Observable

class LoginPresenter : BasePresenterImpl<LoginContract.View>(),
        LoginContract.Presenter {

    override fun loginUser(id : String, pw : String) : Observable<UserVO> {

//        var loginId = AppData.getLoginId()
//        var loginPw = AppData.getLoginPw()
//
//        if((loginId == id) && (loginPw == pw)) {
//            return true
//        }
//        return false

        return APIManager.login(id, pw)
    }
}