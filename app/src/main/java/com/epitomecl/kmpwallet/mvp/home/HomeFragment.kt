package com.epitomecl.kmpwallet.mvp.home

import com.epitomecl.kmpwallet.mvp.BaseFragment

class HomeFragment : BaseFragment<HomeContract.View,
        HomeContract.Presenter>(),
        HomeContract.View {

    override var mPresenter: HomeContract.Presenter = HomePresenter()

    override fun showTest() {
        //
    }
}