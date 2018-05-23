package com.epitomecl.kmpwallet.mvp.wallet.wallets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.mvp.BaseFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.WalletsContract
import com.epitomecl.kmpwallet.mvp.wallet.wallets.WalletsPresenter

class WalletsFragment : BaseFragment<WalletsContract.View,
        WalletsContract.Presenter>(),
        WalletsContract.View {

    override var mPresenter: WalletsContract.Presenter = WalletsPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_wallets, container, false)
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