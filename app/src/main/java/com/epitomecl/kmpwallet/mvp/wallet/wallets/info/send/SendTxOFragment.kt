package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.base.BaseFragment

class SendTxOFragment : BaseFragment<SendTxOContract.View,
        SendTxOContract.Presenter>(),
        SendTxOContract.View {

    override var mPresenter: SendTxOContract.Presenter = SendTxOPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_accounts, container, false)
        var component = getActivityComponent()
        if(component != null){
            //component.inject(this)
            mPresenter.attachView(this)
        }

        return view
    }

}