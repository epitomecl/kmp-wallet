package com.epitomecl.kmp.core.wallet

import org.bitcoinj.crypto.DeterministicKey

data class AccountInput(val utxo : UTXO, val signKey : DeterministicKey)
