package com.epitomecl.kmpwallet.mvp.backup

import com.epitomecl.kmpwallet.mvp.base.BasePresenter
import com.epitomecl.kmpwallet.mvp.base.BaseView

object BackupContract {
    interface View : BaseView {
        fun onCompletedBackup()
    }

    interface Presenter : BasePresenter<View> {
        fun getWords() : String
        fun getWordForIndex(index : Int) : String
        fun getMnemonicSize() : Int
    }
}