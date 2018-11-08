package com.epitomecl.kmpwallet.mvp.wallet.restore

import com.epitomecl.kmp.core.common.SharingData
import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.model.EncryptedResult
import com.epitomecl.kmpwallet.model.SecretSharingVO
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import org.spongycastle.util.encoders.Hex

class RestoreWalletPresenter : BasePresenterImpl<RestoreWalletContract.View>(),
        RestoreWalletContract.Presenter {

    override fun restoredWallets(): List<String> {
        val index: Int = AppData.getUserIndex()!!

        return APIManager.getSharingDataList(index, "api_code")
    }

    override fun restoreWallet(label: String): Boolean {
        val index: Int = AppData.getUserIndex()!!

        val partOne: SecretSharingVO = APIManager.getSharingDataOne(index, label, "api_code")
        val partTwo: SecretSharingVO = APIManager.getSharingDataTwo(index, label, "api_code")
        val parts: MutableMap<Int, ByteArray> = mutableMapOf()
        parts.put(1, Hex.decode(partOne.sharedData))
        parts.put(2, Hex.decode(partTwo.sharedData))

        if((partOne.label != null) && (partTwo.label != null)) {
            val encrypted: EncryptedResult = APIManager.getEncrypted(index, label, "api_code")
            if(encrypted.result != null) {
                val sharingData = SharingData(encrypted.result, parts)
                val seed: String = sharingData.getJoinData()

                AppData.restoreWallet(CryptoType.BITCOIN_TESTNET, seed, label)
                AppData.saveHDWallets()
                return true
            }
            else {
                mView?.showError(R.string.msg_alert_encrypt_error)
            }
        }
        else {
            mView?.showError(R.string.msg_alert_restore_error)
        }
        return false
    }
}
