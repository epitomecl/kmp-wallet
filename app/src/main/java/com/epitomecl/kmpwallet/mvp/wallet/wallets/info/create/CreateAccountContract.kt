package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.create

import android.content.Context
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object CreateAccountContract {
    interface View : BaseView {
        fun onCreateAccounts()
        fun isValidLabel(): Boolean
    }

    interface Presenter : BasePresenter<View> {
        fun createAccount(context: Context?, label: String)
    }
}