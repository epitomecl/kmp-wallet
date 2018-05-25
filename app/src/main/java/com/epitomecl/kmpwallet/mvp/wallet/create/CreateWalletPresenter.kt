package com.epitomecl.kmpwallet.mvp.wallet.create

import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import info.blockchain.wallet.bip44.HDWallet
import info.blockchain.wallet.bip44.HDWalletFactory
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.params.BitcoinTestNet3Params

class CreateWalletPresenter : BasePresenterImpl<CreateWalletContract.View>(),
        CreateWalletContract.Presenter {

    private var wallet: HDWallet? = null

    private val DEFAULT_MNEMONIC_LENGTH = 12
    private val DEFAULT_NEW_WALLET_SIZE = 1
    private val DEFAULT_PASSPHRASE = ""

    override fun createWallet(label: String) {
        var param : NetworkParameters? = null
        param = BitcoinTestNet3Params.get()

        wallet = HDWalletFactory
                .createWallet(param, HDWalletFactory.Language.US,
                        DEFAULT_MNEMONIC_LENGTH, DEFAULT_PASSPHRASE, DEFAULT_NEW_WALLET_SIZE)
    }
}