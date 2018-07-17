package com.epitomecl.kmpwallet.mvp.intro.regist

import com.epitomecl.kmpwallet.model.UserVO
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView
import io.reactivex.Observable

object RegistContract {
    interface View : BaseView {
        //
    }

    interface Presenter : BasePresenter<View> {
        fun registUser(id : String, pw : String) : Observable<UserVO>
    }
}