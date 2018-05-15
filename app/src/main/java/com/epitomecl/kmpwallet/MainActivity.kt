package com.epitomecl.kmpwallet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.epitomecl.kmpwallet.mvp.wallet.WalletFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShowWallet.setOnClickListener{ onClickCreateWallet() }
    }

    private fun onClickCreateWallet() {
        val intent = Intent(this, WalletFragment::class.java)
        startActivity(intent)
    }
}
