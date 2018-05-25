package com.epitomecl.kmpwallet.mvp.wallet

<<<<<<< HEAD
import android.support.v4.app.Fragment
import com.epitomecl.kmpwallet.mvp.BasePresenter
import com.epitomecl.kmpwallet.mvp.BaseView
=======
import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView
>>>>>>> f93a208f1826a932ae49a3471a78949b67879184

object WalletContract {
    interface View : BaseView {
        fun onShowWalletList()
        fun onCreateWallet()
        fun onBackupWallet()
        fun onAccount()
    }

    interface Presenter : BasePresenter<View> {
        //
    }
}
