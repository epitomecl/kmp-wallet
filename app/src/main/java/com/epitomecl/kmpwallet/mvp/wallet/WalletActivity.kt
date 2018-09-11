package com.epitomecl.kmpwallet.mvp.wallet

import android.os.Bundle
import android.view.View
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BaseActivity
import com.epitomecl.kmpwallet.mvp.wallet.create.RestoreWalletFragment

import com.epitomecl.kmpwallet.mvp.wallet.create.CreateWalletFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.WalletsFragment
import kotlinx.android.synthetic.main.activity_wallet.*

class WalletActivity : BaseActivity(),
        WalletContract.View {

    var mPresenter: WalletContract.Presenter = WalletPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        btnRestoreWallet.setOnClickListener { onRestoreWallet() }
        btnCancelRestore.setOnClickListener { onCancelRestore() }

        supportFragmentManager.addOnBackStackChangedListener {
            if(supportFragmentManager.backStackEntryCount == 0) {
                init()
            }
        }

        init()
    }

    private fun init() {
        val wallets: List<HDWalletData> = AppData.getHDWallets()
        if(wallets.isNotEmpty()) {
            onShowWalletList()
        }
        else {
            onCreateWallet()
        }
    }

    private fun buttons(restore: Int, cancel: Int) {
        btnRestoreWallet.visibility = restore
        btnCancelRestore.visibility = cancel
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
        buttons(View.VISIBLE, View.GONE)

        supportFragmentManager.beginTransaction()
                .replace(R.id.flWallet, CreateWalletFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onRestoreWallet() {
        buttons(View.GONE, View.VISIBLE)

        supportFragmentManager.beginTransaction()
                .replace(R.id.flWallet, RestoreWalletFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onCancelRestore() {
        buttons(View.VISIBLE, View.GONE)

        supportFragmentManager.popBackStack()
    }
}
