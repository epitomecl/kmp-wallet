package com.epitomecl.kmp.core.wallet

interface IAPIManager {
    fun spendTXOCount(address: String) : Int
}