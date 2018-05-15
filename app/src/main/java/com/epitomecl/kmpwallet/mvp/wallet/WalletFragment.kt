package com.epitomecl.kmpwallet.mvp.wallet

import android.os.Bundle
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.BaseActivity
import com.epitomecl.kmpwallet.mvp.BaseFragment

class WalletFragment : BaseActivity<WalletContract.View, WalletContract.Presenter>(),
        WalletContract.View {

    override var mPresenter: WalletContract.Presenter = WalletPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
    }

}