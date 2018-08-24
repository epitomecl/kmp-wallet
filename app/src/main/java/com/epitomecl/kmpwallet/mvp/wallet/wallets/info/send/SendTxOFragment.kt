package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
import com.epitomecl.kmpwallet.model.SendTXResult
import com.epitomecl.kmpwallet.model.UTXO
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.InfoActivity
import org.spongycastle.util.encoders.Hex
import kotlinx.android.synthetic.main.fragment_sendtxo.*
import javax.inject.Inject
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.core.Transaction
import java.math.BigInteger


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

        val activity =  (context as InfoActivity)

        val accountData : AccountData = activity.getAccount()

        tvSendFromAddress.text = accountData.cache.receiveAccount
        tvSendFromBalance.text = activity.getString(R.string.label_account_has) + " " + UTXO.satoshiToCoin(accountData.balance).toString() + " " + activity.getString(R.string.crypto_btc_big)

        btnSend.setOnClickListener { onSend() }
    }

    override fun onSend() {
        //for send test
        var hdWalletData = (context as InfoActivity).getHDWalletData()
        var accountData = (context as InfoActivity).getAccount()

        if(isValid(accountData)) {
            var privKeyString : String = accountData.xpriv
            var pubKeyString : String = accountData.xpub

            var toAddress : String = etSendToAddress.text.toString()
            var send_satoshi : Long = etSendSatoshi.text.toString().toLong()

            val hashtx = mPresenter.makeTx(privKeyString, pubKeyString, toAddress,
                    send_satoshi, accountData.utxos)

            val result : SendTXResult = mPresenter.pushTx(hashtx)

            val tx = Transaction(NetworkParameters.testNet(), Hex.decode(hashtx))

            if(tx.isPending) {
                Toast.makeText(context, "send result: PENDING...", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "send result: REJECT", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValid(accountData : AccountData): Boolean {
        if (etSendToAddress.text.length != 34) {
            Toast.makeText(context, getString(R.string.msg_send_invalid_address), Toast.LENGTH_SHORT).show()
            return false
        }
        if(etSendSatoshi.text.toString().toBigInteger() == BigInteger.ZERO) {
            Toast.makeText(context, getString(R.string.msg_send_zero_value), Toast.LENGTH_SHORT).show()
            return false
        }
        if(accountData.balance == 0L) {
            Toast.makeText(context, getString(R.string.msg_send_invalid_account_balance), Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}