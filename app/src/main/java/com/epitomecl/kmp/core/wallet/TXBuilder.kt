package com.epitomecl.kmp.core.wallet

import org.bitcoinj.core.*
import org.bitcoinj.crypto.DeterministicKey
import org.bitcoinj.script.Script
import org.bitcoinj.wallet.KeyChain
import org.spongycastle.util.encoders.Hex

class TXBuilder {

    private var change_satoshi : Long = 0

    fun makeTx(privKeyString: String, pubKeyString: String, toAddress: String, changeAddress: String,
                        send_satoshi: Long, utxos: List<UTXO>) : String {

        val params : NetworkParameters = NetworkParameters.testNet()
        val ctx = Context(params)
        val privateKey = DeterministicKey.deserializeB58(privKeyString, params)
        val dumpedPrivateKey = privateKey.getPrivateKeyEncoded(NetworkParameters.testNet())

        val fee_satoshi : Long = 100000 //fee calcurate?
        change_satoshi = send_satoshi + fee_satoshi

        val input_utxos: List<AccountInput> = calculateChange(params, privKeyString, utxos)

        change_satoshi = Math.abs(change_satoshi)

        val tx : Transaction = Transaction(params)
        tx.getConfidence().setSource(TransactionConfidence.Source.UNKNOWN)
        tx.setPurpose(Transaction.Purpose.USER_PAYMENT)

        var toAddress : Address = Address(params, toAddress)
        tx.addOutput(Coin.valueOf(send_satoshi), toAddress)

        if(change_satoshi > 0) {
            tx.addOutput(Coin.valueOf(change_satoshi), Address(params, changeAddress))
        }

        input_utxos.forEach { v ->
            val script : Script = Script(Hex.decode(v.utxo.scriptBytes))
            var sha256hash : Sha256Hash = Sha256Hash.wrap(v.utxo.hash)
            val outPoint : TransactionOutPoint = TransactionOutPoint(params, v.utxo.index.toLong(), sha256hash)

            var txInput: TransactionInput = tx.addSignedInput(outPoint, script, v.signKey, Transaction.SigHash.ALL, true)
        }

        var hashtx: String = String(Hex.encode(tx.bitcoinSerialize()))

        return hashtx
    }

    private fun calculateChange(params: NetworkParameters, xpriv: String, utxos: List<UTXO>) : List<AccountInput> {
        //val result = mutableListOf<AccountInput>()
        val deriver = AccountKeyDerivation(params, xpriv)

        var utxos_copy = ArrayList(utxos)

        val changes = getInputs(params, deriver, KeyChain.KeyPurpose.CHANGE, utxos_copy)
        val receives = getInputs(params, deriver, KeyChain.KeyPurpose.RECEIVE_FUNDS, utxos_copy)

        val result = mutableListOf<AccountInput>()
        result.addAll(receives)
        result.addAll(changes)

        return result
    }

    private fun getInputs(params: NetworkParameters, deriver: AccountKeyDerivation, purpose: KeyChain.KeyPurpose, utxos: MutableList<UTXO>): List<AccountInput> {
        val result = mutableListOf<AccountInput>()

        val key = deriver.getKey(purpose)
        val address = key.toAddress(params).toBase58();
        val find : UTXO? = utxos.find { v -> v.toAddress.equals(address) }

        if(find != null) {
            change_satoshi -= find.value.toLong()
            utxos.remove(find)

            val input = AccountInput(find, key)
            result.add(input)
        }

        if(change_satoshi < 0) {
            return result
        }

        val childKeyNode = deriver.getChildKeyNode(purpose)
        if (result.size == 0) {
            childKeyNode.addGap()
            if (childKeyNode.gap >= 20) {
                return result
            }
        } else {
            childKeyNode.resetGap()
        }

        val result_next = getInputs(params, deriver, purpose, utxos)

        result.addAll(result_next)

        return result
    }

}