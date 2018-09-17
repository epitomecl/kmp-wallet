package com.epitomecl.kmpwallet.di.component

import com.epitomecl.kmpwallet.TestActivity
import com.epitomecl.kmpwallet.mvp.account.AccountFragment
import com.epitomecl.kmpwallet.mvp.backup.BackupFragment
import com.epitomecl.kmpwallet.mvp.send.SendFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.accounts.AccountsFragment
import com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send.SendTxOFragment
import dagger.Subcomponent
import piuk.blockchain.androidcore.injection.PresenterScope

@PresenterScope
@Subcomponent
interface PresenterComponent {
    fun inject(activity : TestActivity)

    fun inject(fragment : SendFragment)

    fun inject(fragment : AccountsFragment)
    fun inject(fragment : SendTxOFragment)
    fun inject(fragment : AccountFragment)
    fun inject(fragment : BackupFragment)
}