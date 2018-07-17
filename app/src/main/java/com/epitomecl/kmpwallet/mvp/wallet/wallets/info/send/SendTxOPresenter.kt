package com.epitomecl.kmpwallet.mvp.wallet.wallets.info.send

import com.epitomecl.kmpwallet.api.APIManager
import com.epitomecl.kmpwallet.mvp.base.BasePresenterImpl
import org.bitcoinj.core.*
import org.bitcoinj.crypto.DeterministicKey
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.params.BitcoinMainNetParams
import org.bitcoinj.script.Script
import org.spongycastle.util.encoders.Hex
import java.nio.ByteBuffer
import javax.inject.Inject

class SendTxOPresenter @Inject constructor() : BasePresenterImpl<SendTxOContract.View>(),
        SendTxOContract.Presenter {

    override fun makeTx(privKeyString: String, pubKeyString: String, toAddress: String,
                        amount: Long, scriptBytes: String , index: Int, hash: String) : String {

        val params : NetworkParameters = NetworkParameters.testNet()
        val ctx = Context(params)
        val privateKey = DeterministicKey.deserializeB58(privKeyString, params)

        val receiveKey = HDKeyDerivation.deriveChildKey(privateKey, 0)
        val receiveAddress = HDKeyDerivation.deriveChildKey(receiveKey, 0)
        val address = receiveAddress.toAddress(NetworkParameters.testNet()).toBase58()

        val dumpedPrivateKey = privateKey.getPrivateKeyEncoded(NetworkParameters.testNet())
        val privkey : ECKey = dumpedPrivateKey.key
        val pubkey : ECKey = createMasterPubKeyFromXPub(params, pubKeyString)

        var recv : Address = Address(params, toAddress)
        val amount_satoshis : Long = amount
        val fee_satoshis : Long = 100000

        val tx : Transaction = Transaction(params)
        val script : Script = Script(Hex.decode(scriptBytes))//ScriptBuilder.createOutputScript(receiveAddress)
        var sha256hash : Sha256Hash = Sha256Hash.wrap(hash)

        tx.addOutput(Coin.valueOf(amount_satoshis - fee_satoshis), recv) //fee calcurate?

        tx.getConfidence().setSource(TransactionConfidence.Source.SELF)
        tx.setPurpose(Transaction.Purpose.USER_PAYMENT)

        val outPoint : TransactionOutPoint = TransactionOutPoint(params, index.toLong(), sha256hash)
        var txInput: TransactionInput = tx.addSignedInput(outPoint, script, receiveAddress, Transaction.SigHash.ALL, true)

        var hashtx: String = String(Hex.encode(tx.bitcoinSerialize()))

        return hashtx
    }

    override fun pushTx(hashtx: String) : String {
        var result: String = APIManager.pushTX(hashtx,"api_code")
        return result
    }

    @Throws(AddressFormatException::class)
    private fun createMasterPubKeyFromXPub(params: NetworkParameters, xpubstr: String): DeterministicKey {

        val isTestnet = params !is BitcoinMainNetParams

        val xpubBytes = Base58.decodeChecked(xpubstr)

        val bb = ByteBuffer.wrap(xpubBytes)

        val prefix = bb.getInt()

        if (!isTestnet && prefix != 0x0488B21E) {
            throw AddressFormatException("invalid xpub version")
        }
        if (isTestnet && prefix != 0x043587CF) {
            throw AddressFormatException("invalid xpub version")
        }

        val chain = ByteArray(32)
        val pub = ByteArray(33)
        // depth:
        bb.get()
        // parent fingerprint:
        bb.getInt()
        // child no.
        bb.getInt()
        bb.get(chain)
        bb.get(pub)

        return HDKeyDerivation.createMasterPubKeyFromBytes(pub, chain)
    }
}
