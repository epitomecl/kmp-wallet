package com.epitomecl.kmpwallet.mvp.wallet.create

import com.epitomecl.kmp.core.wallet.CryptoType
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

    private val DEFAULT_MNEMONIC_LENGTH = 12
    private val DEFAULT_NEW_WALLET_SIZE = 1
    private val DEFAULT_PASSPHRASE = ""

    private lateinit var cryptoType : CryptoType
    private var wallet: HDWallet? = null

    override fun setCryptoType(type : CryptoType) {
        cryptoType = type
    }

    override fun createWallet(label: String) {
        var param : NetworkParameters? = null
        param = when (cryptoType) {
            CryptoType.BITCOIN -> BitcoinMainNetParams.get()
            CryptoType.BITCOIN_TESTNET -> BitcoinTestNet3Params.get()
            CryptoType.BITCOIN_CASH -> BitcoinCashMainNetParams.get()
            CryptoType.BITCOIN_CASH_TESTNET -> BitcoinCashTestNet3Params.get()
            CryptoType.ETHEREUM -> BitcoinMainNetParams.get()
            CryptoType.ETHEREUM_TESTNET -> BitcoinMainNetParams.get()
        }

        wallet = HDWalletFactory
                .createWallet(param, HDWalletFactory.Language.US,
                        DEFAULT_MNEMONIC_LENGTH, DEFAULT_PASSPHRASE, DEFAULT_NEW_WALLET_SIZE)
    }
}