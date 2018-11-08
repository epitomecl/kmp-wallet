package com.epitomecl.kmpwallet.mvp.intro

import android.content.Intent
import android.os.Bundle
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BaseActivity
import com.epitomecl.kmpwallet.mvp.intro.login.LoginFragment
import com.epitomecl.kmpwallet.mvp.intro.regist.RegistFragment
import com.epitomecl.kmpwallet.mvp.wallet.WalletActivity

class IntroActivity : BaseActivity(), IntroContract.View {

    var mPresenter: IntroContract.Presenter = IntroPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        if(AppData.getLoginType() == AppData.LoginType.NOT_LOGIN) {
            onRegist()
        }
        else {
            onLogin()
        }
    }

    override fun showState(state: String) {
        //
    }

    override fun onLogin() {
        setTitle(getString(R.string.label_intro_login));

        supportFragmentManager.beginTransaction()
                .replace(R.id.flIntro, LoginFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onRegist() {
        setTitle(getString(R.string.label_intro_regist));

        supportFragmentManager.beginTransaction()
                .replace(R.id.flIntro, RegistFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onChangeWalletActivity() {
        val intent = Intent(this, WalletActivity::class.java)
        startActivity(intent)
    }
}