package com.epitomecl.kmpwallet.mvp.intro.login

import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.model.UserVO
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import io.reactivex.Observable

class LoginPresenter : BasePresenterImpl<LoginContract.View>(),
        LoginContract.Presenter {

    override fun loginUser(id : String, pw : String) : Observable<UserVO> {
        return APIManager.login(id, pw)
    }
}