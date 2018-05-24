package com.epitomecl.kmpwallet.mvp.wallet

import android.content.Intent
import android.os.Bundle
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.base.BaseActivity
import com.epitomecl.kmpwallet.mvp.createwallet.CreateWalletActivity
import kotlinx.android.synthetic.main.activity_wallet.*

class WalletActivity : BaseActivity<WalletContract.View, WalletContract.Presenter>(),
        WalletContract.View {

    override var mPresenter: WalletContract.Presenter = WalletPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        btnCreateWalletBTC.setOnClickListener { onCreateWallet() }
        btnCreateWalletBCH.setOnClickListener { onCreateWallet() }
        btnCreateWalletETH.setOnClickListener { onCreateWallet() }
    }

    private fun onCreateWallet()
    {
        val intent = Intent(this, CreateWalletActivity::class.java)
        startActivity(intent)
    }
}