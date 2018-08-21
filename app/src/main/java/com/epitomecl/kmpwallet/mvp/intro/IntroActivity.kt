package com.epitomecl.kmpwallet.mvp.intro

import android.content.Intent
import android.os.Bundle
import com.epitomecl.kmp.core.util.rpc.KmpRPCClient
import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BaseActivity
import com.epitomecl.kmpwallet.mvp.intro.login.LoginFragment
import com.epitomecl.kmpwallet.mvp.intro.regist.RegistFragment
import com.epitomecl.kmpwallet.mvp.wallet.WalletActivity
import kotlin.concurrent.thread

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

        checkBitcoinBlock()
    }

    override fun showState(state: String) {
        //
    }

    override fun onLogin() {
        setTitle("Login");

        supportFragmentManager.beginTransaction()
                .replace(R.id.flIntro, LoginFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onRegist() {
        setTitle("Regist");

        supportFragmentManager.beginTransaction()
                .replace(R.id.flIntro, RegistFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onChangeWalletActivity() {
        val intent = Intent(this, WalletActivity::class.java)
        startActivity(intent)
    }

    private fun checkBitcoinBlock() {
//        thread{
//            var blockCount = KmpRPCClient.get(CryptoType.BITCOIN)?.getBlockCount()
//            blockCount.toString()
//
//            var result = KmpRPCClient.get(CryptoType.BITCOIN)?.validateaddress("n14GGH3QU4bG66S7ptKpixmLUjbtM4woBY")
//            result = ""
//        }
    }
}