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

        val fragment = WalletsFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_wallet, fragment)?.commit()

        btnShowWalletList.setOnClickListener { onShowWalletList() }
        btnCreateWallet.setOnClickListener { onCreateWallet() }
    }

    private fun onShowWalletList()
    {
        val fragment = WalletsFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_wallet, fragment)?.commit()
    }

    private fun onCreateWallet()
    {
        val fragment = CreateWalletFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_wallet, fragment)?.commit()
    }

}