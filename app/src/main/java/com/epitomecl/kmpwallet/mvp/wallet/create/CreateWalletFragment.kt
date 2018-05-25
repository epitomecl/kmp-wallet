package com.epitomecl.kmpwallet.mvp.wallet.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import android.view.animation.TranslateAnimation
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_createwallet.*

class CreateWalletFragment : BaseFragment<CreateWalletContract.View,
        CreateWalletContract.Presenter>(),
        CreateWalletContract.View {

    private val create = "Press button it will make '%s' wallet"

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

}