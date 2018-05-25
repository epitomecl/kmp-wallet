package com.epitomecl.kmpwallet.mvp.wallet

import android.os.Bundle
import com.epitomecl.kmpwallet.R

import com.epitomecl.kmpwallet.mvp.base.BaseActivity
import com.epitomecl.kmpwallet.mvp.wallet.create.CreateWalletFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.WalletsFragment

import kotlinx.android.synthetic.main.activity_wallet.*

class WalletActivity : BaseActivity<WalletContract.View, WalletContract.Presenter>(),
        WalletContract.View {

    override var mPresenter: WalletContract.Presenter = WalletPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        btnShowWalletList.setOnClickListener { onShowWalletList() }
        btnCreateWallet.setOnClickListener { onCreateWallet() }

        onShowWalletList()
    }

    override fun onShowWalletList()
    {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flWallet, WalletsFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onCreateWallet()
    {
        supportFragmentManager.beginTransaction()
                .replace(R.id.flWallet, CreateWalletFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onBackupWallet() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAccount() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
