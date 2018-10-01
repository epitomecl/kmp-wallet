package com.epitomecl.kmpwallet.mvp.wallet.wallets.info

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.epitomecl.kmp.core.wallet.AccountData
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.model.SendTXResult
import com.epitomecl.kmpwallet.mvp.base.BaseActivity
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.accounts.AccountsFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send.SendTxOFragment
import kotlinx.android.synthetic.main.activity_wallet_info.*

class InfoActivity : BaseActivity(),
            InfoContract.View {

    var mPresenter: InfoContract.Presenter = InfoPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_info)

        btnNewAddress.setOnClickListener { onCreateAccount() }
        btnSendCancel.setOnClickListener { onSendCancel() }

        if (intent.hasExtra("walletlabel")) {
            val label = intent.getStringExtra("walletlabel")
            val hdWalletData =  AppData.getHDWallets().find { v -> v.label.equals(label) }
            if (hdWalletData != null) {
                mPresenter.setHDWallet(hdWalletData)
                mPresenter.setSendTXResultList(AppData.getSendTXResultList(label))
            }
            else {
                Toast.makeText(this, "not found wallet label", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(this, "intent has not key-value data", Toast.LENGTH_SHORT).show()
        }

        onShowAccounts()
    }

    private fun buttons(newaddress: Int, cancel: Int) {
        btnNewAddress.visibility = newaddress
        btnSendCancel.visibility = cancel
    }

    override fun onShowAccounts()
    {
        buttons(View.VISIBLE, View.GONE)

        supportFragmentManager.beginTransaction()
                .replace(R.id.flWalletInfo, AccountsFragment.newInstance())
                .addToBackStack(null)
                .commit()

        supportFragmentManager.executePendingTransactions()
    }

    override fun onCreateAccount()
    {
        mPresenter.addAccount("")

        supportFragmentManager.popBackStack()
        onShowAccounts()
    }

    override fun onShowSendTxO()
    {
        buttons(View.GONE, View.VISIBLE)

        supportFragmentManager.beginTransaction()
                .replace(R.id.flWalletInfo, SendTxOFragment.newInstance())
                .addToBackStack(null)
                .commit()
    }

    override fun onSendCancel() {
        onShowAccounts()
    }

    override fun getHDWalletData() : HDWalletData {
        return mPresenter.getHDWallet()
    }

    override fun setAccount(account : AccountData) {
        mPresenter.setAccount(account)
    }

    override fun getAccount() : AccountData {
        return mPresenter.getAccount()
    }

    override fun getSendTXResultList() : List<SendTXResult>? {
        return mPresenter.getSendTXResultList()
    }
}
