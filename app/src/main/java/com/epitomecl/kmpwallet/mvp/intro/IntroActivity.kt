package com.epitomecl.kmpwallet.mvp.intro

import android.os.Bundle
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.base.BaseActivity

class IntroActivity : BaseActivity<IntroContract.View, IntroContract.Presenter>(), IntroContract.View {

    override var mPresenter: IntroContract.Presenter = IntroPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        onLogin()
    }

    override fun showState(state: String) {
        //
    }

    override fun onLogin() {
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.flLogin, LoginFragment())
//                .addToBackStack(null)
//                .commit()
    }

    override fun onRegist() {
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.flLogin, RegistFragment())
//                .addToBackStack(null)
//                .commit()
    }
}