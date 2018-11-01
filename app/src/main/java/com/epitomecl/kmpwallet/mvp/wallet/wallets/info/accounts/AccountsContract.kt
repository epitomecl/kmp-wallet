package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.accounts

import com.epitomecl.kmp.core.wallet.AccountData
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object AccountsContract {
    interface View : BaseView {
        fun onChangeSendTxOFragment(item: AccountData)
    }

    interface Presenter : BasePresenter<View> {
        //
    }
}
