package com.epitomecl.kmpwallet.mvp.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.di.Injector
import com.epitomecl.kmpwallet.mvp.base.BaseFragment
import com.epitomecl.kmpwallet.util.DialogUtils
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_send.*

class SendFragment : BaseFragment(),
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSend.setOnClickListener {

            checkFields()

            mPresenter.submitBitcoinTransaction()
        }
    }

    private fun checkFields() : Boolean {
        if(etFrom.text.toString().length==0){
            DialogUtils.setAlertDialog(context, "from address 를 확인해주세요!")
            return false
        }
        if(etTo.text.toString().length==0){
            DialogUtils.setAlertDialog(context, "from address 를 확인해주세요!")
            return false
        }
        ////amount, fee and etc...

        return true
    }

    override fun showTransactionSuccess(hash: String, transactionValue: Long, currency: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        //open transaction success dialog
        Toast.makeText(context, "Transaction Success", Toast.LENGTH_LONG)
    }


}