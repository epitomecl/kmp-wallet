package com.epitomecl.kmpwallet.mvp.backup

import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import piuk.blockchain.androidcore.data.payload.PayloadDataManager
import piuk.blockchain.androidcore.utils.helperfunctions.unsafeLazy
import javax.inject.Inject


class BackupPresenter @Inject constructor(
    private val backupWalletUtil: BackupWalletUtil,
    private val payloadDataManager: PayloadDataManager
): BasePresenterImpl<BackupContract.View>(),
        BackupContract.Presenter {

    private var mnemonic: List<String>? = null

    private val sequence by unsafeLazy { getBackupConfirmSequence() }

    override fun getWords(): String {
        mnemonic = backupWalletUtil.getMnemonic(null)
        return mnemonic.toString()
    }

    internal fun showWordHints() : List<Int>{
        return listOf(sequence[0].first, sequence[1].first, sequence[2].first)
    }

    internal fun onVerifyClicked(firstWord: String, secondWord: String, thirdWord: String) {
        if (firstWord.trim { it <= ' ' }.equals(sequence[0].second, ignoreCase = true)
                && secondWord.trim { it <= ' ' }.equals(sequence[1].second, ignoreCase = true)
                && thirdWord.trim { it <= ' ' }.equals(sequence[2].second, ignoreCase = true)
        ) {
            updateBackupStatus()
        } else {
            mView?.showError("Word Mismatch")
        }
    }

    internal fun updateBackupStatus() {
        payloadDataManager.wallet!!.hdWallets[0].isMnemonicVerified = true

        payloadDataManager.syncPayloadWithServer()
                .doOnSubscribe { mView?.showLoading() }
                .doAfterTerminate { mView?.hideLoading() }
                .subscribe(
                        {
                            //save data to sharedpreference
                            //

                            mView?.onCompletedBackup()
                        },
                        {
                            mView?.showError("Unable to connect to server. Please try again later")
                        }
                )
    }

    private fun getBackupConfirmSequence() : List<Pair<Int, String>> {
        return backupWalletUtil.getConfirmSequence(null)
    }

    override fun getWordForIndex(index: Int) = mnemonic!![index]

    override fun getMnemonicSize() = mnemonic?.size ?: -1
}