package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.create

import android.content.Context
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.InfoActivity

class CreateAccountPresenter : BasePresenterImpl<CreateAccountContract.View>(),
        CreateAccountContract.Presenter {

    override fun createAccount(context: Context?, label: String) {
        (context as InfoActivity).getHDWalletData().addAccount(label)
        AppData.saveHDWallets()
    }
}
