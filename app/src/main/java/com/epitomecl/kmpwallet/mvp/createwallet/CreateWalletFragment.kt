package com.epitomecl.kmpwallet.mvp.createwallet

import android.os.Bundle
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.BaseActivity
import com.epitomecl.kmpwallet.mvp.BaseFragment

class CreateWalletFragment : BaseActivity<CreateWalletContract.View, CreateWalletContract.Presenter>(),
        CreateWalletContract.View {

    override var mPresenter: CreateWalletContract.Presenter = CreateWalletPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creatwallet)
    }

}