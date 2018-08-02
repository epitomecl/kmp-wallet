package com.epitomecl.kmpwallet.model

import org.bitcoinj.crypto.DeterministicKey

data class AccountInput(val utxo : UTXO, val signKey : DeterministicKey)
