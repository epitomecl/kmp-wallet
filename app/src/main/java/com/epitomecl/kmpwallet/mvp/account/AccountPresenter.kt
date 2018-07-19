package com.epitomecl.kmpwallet.mvp.account

import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import javax.inject.Inject

class AccountPresenter @Inject constructor(

): BasePresenterImpl<AccountContract.View>(),
        AccountContract.Presenter {

    /* refer from block.info */
    internal fun createNewAccount(accountLabel : String) {
        //check if the label already used.
        ///


    }
}