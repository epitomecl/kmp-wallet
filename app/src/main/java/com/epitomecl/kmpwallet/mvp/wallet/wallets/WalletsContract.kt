package com.epitomecl.kmpwallet.mvp.wallet.wallets

import com.epitomecl.kmpwallet.mvp.BasePresenter
import com.epitomecl.kmpwallet.mvp.BaseView

object WalletsContract {
    interface View : BaseView {

    }

    interface Presenter : BasePresenter<View> {
        fun initWallets() : ArrayList<String>
    }
}