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
import com.epitomecl.kmpwallet.model.UTXO
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.InfoActivity
import info.blockchain.wallet.util.HexUtils
import kotlinx.android.synthetic.main.fragment_sendtxo.*
import org.bitcoinj.core.*
import org.bitcoinj.script.Script
import org.bitcoinj.script.ScriptBuilder
import org.bitcoinj.wallet.SendRequest
import org.spongycastle.util.encoders.Hex

class SendTxOFragment : BaseFragment<SendTxOContract.View,
        SendTxOContract.Presenter>(),
        SendTxOContract.View {

    lateinit var sendAccount : AccountData
    lateinit var sendWallet : HDWalletData

    override var mPresenter: SendTxOContract.Presenter = SendTxOPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_sendtxo, container, false)
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

        tvSendFromAddress.text = (context as InfoActivity).getAccount().cache.receiveAccount

        btnSend.setOnClickListener { onSend() }
    }

    override fun onSend() {
        //for send test
        var hdWalletData = (context as InfoActivity).getHDWalletData()
        var accountData = (context as InfoActivity).getAccount()
        var privKeyString = accountData.xpriv

        var toAddress : String = etSendToAddress.text.toString()
        var amount : Long = etSendSatoshi.text.toString().toLong()

        var utxo: UTXO = accountData.utxos.get(0)
        var index = utxo.index
        var sha256hash = Sha256Hash.wrap(utxo.hash)
        var builder = ScriptBuilder()
        builder.data(HexUtils.decodeHex(utxo.scriptBytes.toCharArray()))
        var script: Script = builder.build()

        val params : NetworkParameters = NetworkParameters.testNet()
        val dumpedPrivateKey : DumpedPrivateKey = DumpedPrivateKey.fromBase58(params, privKeyString);
        val key : ECKey = dumpedPrivateKey.key

        var recv : Address = Address(params, toAddress)
        val amount_satoshis : Long = amount

        val tx : Transaction = Transaction(params)

        tx.addOutput(Coin.valueOf(amount_satoshis-4013), recv)
        //tx.getHashAsString()

        tx.getConfidence().setSource(TransactionConfidence.Source.SELF)
        tx.setPurpose(Transaction.Purpose.USER_PAYMENT)

        val outPoint : TransactionOutPoint = TransactionOutPoint(params, index as Long, sha256hash)
        var txInput: TransactionInput = tx.addSignedInput(outPoint, script, key, Transaction.SigHash.ALL, true)

        var hashtx: String = String(Hex.encode(tx.bitcoinSerialize()))
        val tx_copy : Transaction = Transaction(params, Hex.decode(hashtx))

        val result : String = mPresenter.send(hashtx)
        Toast.makeText(context, "send result: $result" + result, Toast.LENGTH_SHORT).show()
    }
}