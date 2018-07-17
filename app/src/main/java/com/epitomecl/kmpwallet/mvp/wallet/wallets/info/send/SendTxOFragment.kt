package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Toast
import com.epitomecl.kmp.core.wallet.AccountData
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.di.Injector
import com.epitomecl.kmpwallet.model.UTXO
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.InfoActivity
import kotlinx.android.synthetic.main.fragment_sendtxo.*
import javax.inject.Inject

class SendTxOFragment : BaseFragment<SendTxOContract.View,
        SendTxOPresenter>(),
        SendTxOContract.View {

    companion object {
        fun newInstance(): SendTxOFragment {
            val fragment = SendTxOFragment()
            val bundle = Bundle()
            fragment.setArguments(bundle)
            return fragment
        }
    }

    @Inject
    lateinit var mPresenter: SendTxOPresenter

    init {
        Injector.getInstance().getPresenterComponent().inject(this)
    }

    override fun createPresenter() = mPresenter

    override fun getMvpView() = this

    lateinit var sendAccount : AccountData
    lateinit var sendWallet : HDWalletData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_sendtxo, container, false)
//        var component = getActivity()
//        if(component != null){
//            //component.inject(this)
//            mPresenter.attachView(this)
//        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val anim = TranslateAnimation(1000f, // fromXDelta
                0f, // toXDelta
                0f, // fromYDelta
                0f)// toYDelta
        anim.duration = 300
        view.startAnimation(anim)

        tvSendFromAddress.text = (context as InfoActivity).getAccount().cache.receiveAccount

        btnSend.setOnClickListener { onSend() }
    }

    override fun onSend() {
        //for send test
        var hdWalletData = (context as InfoActivity).getHDWalletData()
        var accountData = (context as InfoActivity).getAccount()
        var privKeyString : String = accountData.xpriv
        var pubKeyString : String = accountData.xpub

        var toAddress : String = etSendToAddress.text.toString()
        var amount : Long = etSendSatoshi.text.toString().toLong()

        var utxo: UTXO = accountData.utxos.get(0)

        val hashtx = mPresenter.makeTx(privKeyString, pubKeyString, toAddress,
                amount, utxo.scriptBytes , utxo.index, utxo.hash)

        val result : String = mPresenter.pushTx(hashtx)

        Toast.makeText(context, "send result: $result" + result, Toast.LENGTH_SHORT).show()
    }

}