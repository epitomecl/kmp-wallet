package com.epitomecl.kmpwallet.mvp.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.base.BaseFragment

class SendFragment : BaseFragment<SendContract.View,
        SendContract.Presenter>(),
        SendContract.View {

    override var mPresenter: SendContract.Presenter = SendPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_send, container, false)
        var component = getActivityComponent()
        if(component != null){
            component.inject(this)
            mPresenter.attachView(this)
        }
        return view
    }



}