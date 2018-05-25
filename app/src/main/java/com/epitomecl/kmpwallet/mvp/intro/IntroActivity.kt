package com.epitomecl.kmpwallet.mvp.intro

import android.os.Bundle
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.base.BaseActivity
import com.epitomecl.kmpwallet.mvp.intro.login.LoginFragment
import com.epitomecl.kmpwallet.mvp.intro.regist.RegistFragment

class IntroActivity : BaseActivity<IntroContract.View, IntroContract.Presenter>(), IntroContract.View {

    override var mPresenter: IntroContract.Presenter = IntroPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        onLogin()
    }

    override fun showState(state: String) {
        //
    }

    override fun onLogin() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flIntro, LoginFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onRegist() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flIntro, RegistFragment())
                .addToBackStack(null)
                .commit()
    }
}