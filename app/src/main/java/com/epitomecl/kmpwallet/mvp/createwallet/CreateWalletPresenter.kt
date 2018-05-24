package com.epitomecl.kmpwallet.mvp.createwallet

import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import info.blockchain.wallet.bip44.HDWallet
import info.blockchain.wallet.bip44.HDWalletFactory
import org.bitcoinj.core.NetworkParameters

class CreateWalletPresenter : BasePresenterImpl<CreateWalletContract.View>(),
        CreateWalletContract.Presenter {

    private var wallet: HDWallet? = null

    private val DEFAULT_MNEMONIC_LENGTH = 12
    private val DEFAULT_NEW_WALLET_SIZE = 1
    private val DEFAULT_PASSPHRASE = ""

    override fun createWallet(label: String) {
        var param : NetworkParameters? = null
        param = NetworkParameters.testNet();

        wallet = HDWalletFactory
                .createWallet(param, HDWalletFactory.Language.US,
                        DEFAULT_MNEMONIC_LENGTH, DEFAULT_PASSPHRASE, DEFAULT_NEW_WALLET_SIZE)
    }
}