package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import com.epitomecl.kmp.core.wallet.UTXO
import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.InfoActivity
import org.spongycastle.util.encoders.Hex
import kotlinx.android.synthetic.main.fragment_sendtxo.*
import kotlinx.android.synthetic.main.item_sendtx_result.view.*
import okhttp3.ResponseBody
import javax.inject.Inject
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.core.Transaction
import org.json.JSONObject
import retrofit2.Response
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

        val activity =  getInfoActivity()

        val accountData : AccountData = activity.getAccount()

        tvSendFromAddress.text = accountData.cache.receiveAccount
        tvSendFromBalance.text = activity.getString(R.string.label_account_has) + " " + UTXO.satoshiToCoin(accountData.balance).toString() + " " + activity.getString(R.string.crypto_btc_big)

        btnSend.setOnClickListener { onSend() }

        val items : List<SendTXResult>? = getInfoActivity().getSendTXResultList()?.toMutableList()
        rvSendTXResult.layoutManager = LinearLayoutManager(context)
        rvSendTXResult.adapter = SendTXResultItemAdapter(items, context!!, this)
    }

    override fun onSend() {
        //for send test
        var hdWalletData = getInfoActivity().getHDWalletData()
        var accountData = getInfoActivity().getAccount()

        if(isValid(accountData)) {
            var privKeyString : String = accountData.xpriv
            var pubKeyString : String = accountData.xpub

            var toAddress : String = etSendToAddress.text.toString()
            var send_satoshi : Long = etSendSatoshi.text.toString().toLong()

            val hashtx = mPresenter.makeTx(privKeyString, pubKeyString, toAddress,
                    send_satoshi, accountData.utxos)

            try {
                val result : SendTXResult = mPresenter.pushTx(hashtx)

                if(result.error != null) {
                    val tx = Transaction(NetworkParameters.testNet(), Hex.decode(hashtx))

                    if(tx.isPending) {
                        Toast.makeText(context, "send result: PENDING...", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(context, "send result: REJECT", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(context, "send result: ERROR => " + result.error, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {

            }

            val sendResult : SendTXResult = SendTXResult(hashtx, "")
            AppData.addSendTXResult(hdWalletData.label, sendResult)
            AppData.saveHDWallets()
            getInfoActivity().onShowAccounts()
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
        if(accountData.balance == BigInteger.ZERO) {
            Toast.makeText(context, getString(R.string.msg_send_invalid_account_balance), Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun getInfoActivity() : InfoActivity {
        return (activity as InfoActivity)
    }

    class SendTXResultItemAdapter(private val items : List<SendTXResult>?, val context: Context, private val fragment: SendTxOFragment) : RecyclerView.Adapter<ViewHolder>() {

        override fun getItemCount(): Int {
            if(items != null) {
                return items.size
            }
            return 0
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sendtx_result, parent, false), fragment)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            val sendTXResult: SendTXResult = items!![position]
            holder?.tvTXID?.text = ""
            holder?.tvSendToAddress?.text = ""
            holder?.tvSendAmount?.text = "0.0"
            holder?.tvSendTXConfirm?.text = "0 Confirm"
            holder?.bind(sendTXResult)
        }
    }

    class ViewHolder (view: View, fragment: SendTxOFragment) : RecyclerView.ViewHolder(view) {
        val view = view
        val fragment = fragment
        // hold ui elements
        val tvTXID = view.tvTXID
        val tvSendToAddress = view.tvSendToAddress
        val tvSendAmount = view.tvSendAmount
        val tvSendTXConfirm = view.tvSendTXConfirm

        fun bind(item: SendTXResult) {
            val tx = Transaction(NetworkParameters.testNet(), Hex.decode(item.hashtx))

            tvTXID.text = tx.hashAsString
            tvSendToAddress.text = tx.getOutput(0).getAddressFromP2PKHScript(NetworkParameters.testNet())?.toBase58()
            tvSendAmount.text = tx.getOutput(0).value.toFriendlyString()

            val json_tx : Response<ResponseBody> = APIManager.getTransactionInfo(tx.hashAsString)
            if(json_tx!= null) {
                val label : String = fragment.getInfoActivity().getHDWalletData().label
                var confirm : Int = 6

                if(json_tx.body() != null) {
                    val objects = JSONObject(json_tx.body()?.string())
                    val confirmations = objects.optString("confirmations")
                    confirm = confirmations.toInt()

                    tvSendTXConfirm.text = confirmations  + " Confirm"
                }

                if(confirm >= 6) {
                    //remove send transaction that found more then 6 confirms
                    AppData.delSendTXResult(label, item)
                }
            }
        }
    }
}