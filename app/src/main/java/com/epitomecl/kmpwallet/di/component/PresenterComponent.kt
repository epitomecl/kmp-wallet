package com.epitomecl.kmpwallet.di.component

import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.accounts.AccountsFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send.SendTxOFragment
import dagger.Subcomponent

@Subcomponent
interface PresenterComponent {
    fun inject(fragment : AccountsFragment)
    fun inject(fragment : SendTxOFragment)
}