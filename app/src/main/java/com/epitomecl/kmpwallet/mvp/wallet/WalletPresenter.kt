package com.epitomecl.kmpwallet.mvp.wallet

import com.epitomecl.kmp.core.common.SharingData
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.model.EncryptedResult
import com.epitomecl.kmpwallet.model.SecretSharingResult
import com.epitomecl.kmpwallet.model.SecretSharingVO
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import org.spongycastle.util.encoders.Hex

class WalletPresenter : BasePresenterImpl<WalletContract.View>(),
        WalletContract.Presenter {

    override fun isBackupedWallet(label: String): Boolean {
        val index: Int = AppData.getUserIndex()!!
        val result: SecretSharingVO = APIManager.getSharingDataOne(index, label, "api_code")
        if(result.label != null) {
            return true
        }
        return false
    }

    override fun backupWallet(wallet: HDWalletData): Boolean {
        val index: Int = AppData.getUserIndex()!!
        val label = wallet.label
        val sharingData = SharingData(wallet.seedHex)
        val parts: Map<Int, ByteArray> = sharingData.getSharingParts()
        val one = Hex.toHexString(parts.get(1))
        val two = Hex.toHexString(parts.get(2))

        val resultOne: SecretSharingResult = APIManager.backupSharingDataOne(index, label, one, "api_code")
        if(resultOne.result.equals("ok")) {
            val resultTwo: SecretSharingResult = APIManager.backupSharingDataTwo(index, label, two, "api_code")
            if(resultTwo.result.equals("ok")) {
                val resultEncrypted: EncryptedResult = APIManager.setEncrypted(index, label, sharingData.encrypted, "api_code")
                if(resultEncrypted.result.equals("ok")) {
                    mView?.showError(R.string.msg_backup_wallet_success)
                    return true
                }
            }
        }
        mView?.showError(R.string.msg_backup_wallet_fail)
        return false
    }
}