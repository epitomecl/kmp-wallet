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

    val mWallets: List<HDWalletData> = AppData.getHDWallets()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        btnRestoreWallet.setOnClickListener { onRestoreWallet() }
        btnBackupWallet.setOnClickListener { onBackupWallet() }
        btnCancelRestore.setOnClickListener { onCancelRestore() }

        supportFragmentManager.addOnBackStackChangedListener {
            if(supportFragmentManager.backStackEntryCount == 0) {
                init()
            }
        }

        init()
    }

    private fun init() {
        if(mWallets.isNotEmpty()) {
            onShowWalletList()
        }
        else {
            onCreateWallet()
        }
    }

    private fun buttons(restore: Int, backup: Int, cancel: Int) {
        btnRestoreWallet.visibility = restore
        btnBackupWallet.visibility = backup
        btnCancelRestore.visibility = cancel
    }

    override fun onShowWalletList()
    {
        val label = mWallets.get(0).label
        if(mPresenter.isBackupedWallet(label)) {
            buttons(View.GONE, View.GONE, View.GONE)
        }
        else {
            buttons(View.GONE, View.VISIBLE, View.GONE)
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.flWallet, WalletsFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onCreateWallet()
    {
        buttons(View.VISIBLE, View.GONE, View.GONE)

        supportFragmentManager.beginTransaction()
                .replace(R.id.flWallet, CreateWalletFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onRestoreWallet() {
        buttons(View.GONE, View.GONE, View.VISIBLE)

        supportFragmentManager.beginTransaction()
                .replace(R.id.flWallet, RestoreWalletFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun onBackupWallet() {
        buttons(View.GONE, View.VISIBLE, View.GONE)

        if(mPresenter.backupWallet(AppData.getHDWallets().get(0))) {
            onShowWalletList()
        }
    }

    override fun onCancelRestore() {
        buttons(View.VISIBLE, View.GONE, View.GONE)

        supportFragmentManager.popBackStack()
    }
}
