package com.epitomecl.kmpwallet.mvp.wallet.wallets

import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import com.epitomecl.kmpwallet.mvp.wallet.wallets.WalletsContract

class WalletsPresenter : BasePresenterImpl<WalletsContract.View>(),
        WalletsContract.Presenter {

    override fun initWallets() : ArrayList<String> {
        val items = ArrayList<String>()

        for( i in 0..1 ) {
            items.add("내 지갑 $i")
        }

        return items
    }
}