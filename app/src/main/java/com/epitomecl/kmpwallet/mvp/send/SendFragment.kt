package com.epitomecl.kmpwallet.mvp.send

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.data.Constants
import com.epitomecl.kmpwallet.di.Injector
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.util.DialogUtils
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_send.*

class SendFragment : BaseFragment<SendContract.View, SendPresenter>(),
        SendContract.View {

    companion object {
        fun newInstance(): SendFragment {
            val fragment = SendFragment()
            val bundle = Bundle()
            fragment.setArguments(bundle)
            return fragment
        }
    }

    @Inject
    lateinit var mPresenter: SendPresenter

    init {
        Injector.getInstance().getPresenterComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_send, container, false)
//        var component = getActivityComponent()
//        if(component != null){
//            component.inject(this)
//            mPresenter.attachView(this)
//        }
        return view
    }

    override fun createPresenter() = mPresenter

    override fun getMvpView() = this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSend.setOnClickListener {

            if(checkFields()){
                mPresenter.submitBitcoinTransaction()
            }

        }

//        mPresenter.sendFromBTCViaRpc("test", "2MzUwafQ7obPtDbX8Toc3fPo4c9rt9gKupA", 0.0001)
//        mPresenter.getTransactionViaRpc("9146d398724df3535a75f715e0468e1c9ebc1dcd690264ca3bd362da47eba94e")
    }

    private fun checkFields() : Boolean {
        if(etFrom.text.toString().length==0){
            DialogUtils.setAlertDialog(context, "from address 를 확인해주세요!")
            return false
        }
        if(etTo.text.toString().length==0){
            DialogUtils.setAlertDialog(context, "to address 를 확인해주세요!")
            return false
        }
        ////amount, fee and etc...

        return true
    }

    override fun showTransactionSuccess(hash: String, transactionValue: Long, currency: String) {
        Log.d(Constants.TAG, "hash:"+hash +"/"+transactionValue+"/"+currency)
        //open transaction success dialog
        Toast.makeText(context, "Transaction Success", Toast.LENGTH_LONG).show()
    }


    override fun onSendSuccess(txid: String?) {
        Log.d(Constants.TAG, "txid:"+txid)
        Toast.makeText(context, "Txid: "+txid, Toast.LENGTH_LONG).show()
    }
}