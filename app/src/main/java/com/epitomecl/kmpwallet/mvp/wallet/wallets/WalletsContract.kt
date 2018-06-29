package com.epitomecl.kmpwallet.mvp.wallet.wallets

import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object WalletsContract {
    interface View : BaseView {
        fun onChangeInfoActivity(item: HDWalletData)
    }

    interface Presenter : BasePresenter<View> {
        fun initWallets() : List<HDWalletData>
    }
}