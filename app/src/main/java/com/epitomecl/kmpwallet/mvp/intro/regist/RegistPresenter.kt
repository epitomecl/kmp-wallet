package com.epitomecl.kmpwallet.mvp.intro.regist

import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl

class RegistPresenter : BasePresenterImpl<RegistContract.View>(),
        RegistContract.Presenter {

    override fun registUser(id : String, pw : String) {
        AppData.setLoginType(AppData.LoginType.ID_LOGIN)
        AppData.setLoginId(id)
        AppData.setLoginPw(pw)
    }
}