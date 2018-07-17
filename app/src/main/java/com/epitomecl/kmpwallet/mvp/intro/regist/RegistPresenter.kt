package com.epitomecl.kmpwallet.mvp.intro.regist

import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.model.UserVO
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import io.reactivex.Observable

class RegistPresenter : BasePresenterImpl<RegistContract.View>(),
        RegistContract.Presenter {

    override fun registUser(id : String, pw : String) : Observable<UserVO> {

        AppData.setLoginType(AppData.LoginType.ID_LOGIN)
        AppData.setLoginId(id)
        AppData.setLoginPw(pw)

        return APIManager.regist(id, pw)
    }
}