package com.epitomecl.kmpwallet.mvp.wallet.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epitomecl.kmpwallet.R
import android.view.animation.TranslateAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_createwallet.*

class CreateWalletFragment : BaseFragment(),
        CreateWalletContract.View {

    private lateinit var cryptoType : CryptoType

    var mPresenter: CreateWalletContract.Presenter = CreateWalletPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_createwallet, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val anim = TranslateAnimation(1000f, // fromXDelta
                0f, // toXDelta
                0f, // fromYDelta
                0f)// toYDelta
        anim.duration = 300
        view.startAnimation(anim)

        init()
    }

    override fun init() {
        btnCreateWallet.setOnClickListener { onClickCreate() }

        createCryptoTypeSpinner()
    }

    override fun onClickCreate() {
        if (isValidLabel()) {
            mPresenter.createWallet(cryptoType, etWalletLabel.text.toString())
            fragmentManager?.popBackStack()
        }
    }

    override fun isValidLabel(): Boolean {
        if (etWalletLabel.text.length > 4) {
            return true
        }

        Toast.makeText(context, getString(R.string.msg_wallet_label_short), Toast.LENGTH_SHORT).show()

        return false
    }

    override fun createCryptoTypeSpinner() {
        spCryptoType.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, CryptoType.values())
        spCryptoType.setPromptId(R.string.msg_select_crypto_type)
        spCryptoType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setCryptoType(CryptoType.valueOf(parent?.getItemAtPosition(position).toString()))
            }
        }

        setCryptoType(CryptoType.BITCOIN)
    }

    override fun setCryptoType(type : CryptoType) {
        var create : String = getString(R.string.label_create_wallet_step3)

        tvCreateMsg.text = when (type) {
            CryptoType.BITCOIN ->  create.format(getString(R.string.crypto_btc_big))
            CryptoType.BITCOIN_TESTNET -> create.format(getString(R.string.crypto_btc_test_big))
            CryptoType.BITCOIN_CASH -> create.format(getString(R.string.crypto_bch_big))
            CryptoType.BITCOIN_CASH_TESTNET -> create.format(getString(R.string.crypto_bch_test_big))
            CryptoType.ETHEREUM -> create.format(getString(R.string.crypto_eth_big))
            CryptoType.ETHEREUM_TESTNET -> create.format(getString(R.string.crypto_eth_test_big))
        }

        cryptoType = type
    }

}