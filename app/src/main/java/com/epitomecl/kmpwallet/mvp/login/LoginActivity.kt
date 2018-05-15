package com.epitomecl.kmpwallet.mvp.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.BaseActivity
import com.epitomecl.kmpwallet.mvp.home.HomeContract
import com.epitomecl.kmpwallet.mvp.home.HomePresenter

class LoginActivity : BaseActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    override var mPresenter: LoginContract.Presenter = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun showState(state: String) {
        //
    }

}