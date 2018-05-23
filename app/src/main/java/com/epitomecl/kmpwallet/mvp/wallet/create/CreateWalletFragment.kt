package com.epitomecl.kmpwallet.mvp.wallet.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.BaseFragment
import android.view.animation.TranslateAnimation



class CreateWalletFragment : BaseFragment<CreateWalletContract.View,
        CreateWalletContract.Presenter>(),
        CreateWalletContract.View {

    override var mPresenter: CreateWalletContract.Presenter = CreateWalletPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_createwallet, container, false)
        //var component = getActivityComponent()
        //if(component != null){
        //    component.inject(this)
        //    mPresenter.attachView(this)
        //}

        val anim = TranslateAnimation(1000f, // fromXDelta
                0f, // toXDelta
                0f, // fromYDelta
                0f)// toYDelta
        anim.duration = 300
        view.startAnimation(anim)

        return view
    }

}