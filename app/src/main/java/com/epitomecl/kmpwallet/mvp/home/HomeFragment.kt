package com.epitomecl.kmpwallet.mvp.home

import com.epitomecl.kmpwallet.mvp.base.BaseFragmentOld

class HomeFragment : BaseFragmentOld<HomeContract.View,
        HomeContract.Presenter>(),
        HomeContract.View {

    override var mPresenter: HomeContract.Presenter = HomePresenter()

    override fun showTest() {
        //
    }
}