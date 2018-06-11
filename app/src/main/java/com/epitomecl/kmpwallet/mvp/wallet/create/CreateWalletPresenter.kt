package com.epitomecl.kmpwallet.mvp.wallet.create

import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmpwallet.data.AppData
import com.epitomecl.kmpwallet.data.WalletManager
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import info.blockchain.wallet.bip44.HDWallet
import info.blockchain.wallet.bip44.HDWalletFactory
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.params.BitcoinCashMainNetParams
import org.bitcoinj.params.BitcoinCashTestNet3Params
import org.bitcoinj.params.BitcoinMainNetParams
import org.bitcoinj.params.BitcoinTestNet3Params

class CreateWalletPresenter : BasePresenterImpl<CreateWalletContract.View>(),
        CreateWalletContract.Presenter {

    override fun createWallet(cryptoType : CryptoType, label: String) {
        AppData.createWallet(cryptoType, label)
    }
}
