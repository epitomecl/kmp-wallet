package com.epitomecl.kmpwallet.mvp.createwallet

import android.os.Bundle
import android.widget.Toast
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.BaseActivity
import kotlinx.android.synthetic.main.activity_creatwallet.*

class CreateWalletActivity : BaseActivity<CreateWalletContract.View, CreateWalletContract.Presenter>(),
        CreateWalletContract.View {

    override var mPresenter: CreateWalletContract.Presenter = CreateWalletPresenter()

    private val create = "Press button it will make '%s' wallet"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creatwallet)

        //need intent
        tvCreateMsg.text = create.format("BTC")

        btnCreateWallet.setOnClickListener { onClickCreate() }
        btnCreateWalletCancel.setOnClickListener { onClickCancel() }
    }

    private fun onClickCreate() {
        if (isvalidLabel()) {
            mPresenter.createWallet(etWalletLabel.text.toString())
        }
    }

    private fun onClickCancel() {
        finish()
    }

    private fun isvalidLabel(): Boolean {
        if (etWalletLabel.text.length > 4) {
            return true
        }

        Toast.makeText(this, "wallet label is too short.", Toast.LENGTH_SHORT).show()

        return false
    }

}