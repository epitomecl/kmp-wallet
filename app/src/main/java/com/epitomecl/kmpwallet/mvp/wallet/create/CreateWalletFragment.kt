package com.epitomecl.kmpwallet.mvp.wallet.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import android.view.animation.TranslateAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_createwallet.*

class CreateWalletFragment : BaseFragment<CreateWalletContract.View,
        CreateWalletContract.Presenter>(),
        CreateWalletContract.View {

    private val create = "3. Press button it will make '%s' wallet"

    override var mPresenter: CreateWalletContract.Presenter = CreateWalletPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_createwallet, container, false)
        var component = getActivityComponent()
        if(component != null){
            //component.inject(this)
            mPresenter.attachView(this)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val anim = TranslateAnimation(1000f, // fromXDelta
                0f, // toXDelta
                0f, // fromYDelta
                0f)// toYDelta
        anim.duration = 300
        view.startAnimation(anim)

        //need intent
        tvCreateMsg.text = create.format("BTC")

        btnCreateWallet.setOnClickListener { onClickCreate() }

        initCryptoTypeSpinner()
    }

    private fun onClickCreate() {
        if (isvalidLabel()) {
            mPresenter.createWallet(etWalletLabel.text.toString())
        }
    }

    private fun isvalidLabel(): Boolean {
        if (etWalletLabel.text.length > 4) {
            return true
        }

        Toast.makeText(context, "wallet label is too short.", Toast.LENGTH_SHORT).show()

        return false
    }

    private fun initCryptoTypeSpinner() {
        val cryptoTypes = arrayOf(getString(R.string.crypto_btc_big), getString(R.string.crypto_bch_big), getString(R.string.crypto_eth_big))
        spCryptoType.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, cryptoTypes)
        spCryptoType.setPromptId(R.string.msg_select_crypto_type)
        spCryptoType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(context, cryptoTypes[position], Toast.LENGTH_LONG).show()
            }
        }
    }

}