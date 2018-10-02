package com.epitomecl.kmpwallet.data

import android.content.Context
import android.content.SharedPreferences
import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.model.SendTXResult
import com.epitomecl.kmpwallet.util.SharedPreferenceSecure

class AppData(val application: Context, val sharedPreferences : SharedPreferences) {

    enum class LoginType {
        NOT_LOGIN,
        ID_LOGIN,
        EMAIL_LOGIN,
        GUID_LOGIN;

        companion object {
            fun from(findValue: Int): LoginType = LoginType.values().first { it.ordinal == findValue }
        }
    }

    private var prefs : SharedPreferenceSecure = SharedPreferenceSecure(application, sharedPreferences)
    private var walletManager : WalletManager

    init {
        mAppData = this
        walletManager = WalletManager()

        initWallets()
    }

    private var logintype : Int = 0
        get() = prefs.getInt(application.getString(R.string.key_logintype), 0)
        set(value) {
            prefs.edit().putInt(application.getString(R.string.key_logintype), value as Int).commit()
            field = value
        }

    private var userIndex : Int? = -1
        get() = prefs.getInt(application.getString(R.string.key_user_index), -1)
        set(value) {
            prefs.edit().putInt(application.getString(R.string.key_user_index), value as Int).commit()
            field = value
        }

    private var loginId : String? = ""
        get() = prefs.getString(application.getString(R.string.key_login_id), "")
        set(value) {
            prefs.edit().putString(application.getString(R.string.key_login_id), value).commit()
            field = value
        }

    private var loginPw : String? = ""
        get() = prefs.getString(application.getString(R.string.key_login_pw), "")
        set(value) {
            prefs.edit().putString(application.getString(R.string.key_login_pw), value).commit()
            field = value
        }

    private var wallets : String? = ""
        get() = prefs.getString(application.getString(R.string.key_wallets), "")
        set(value) {
            prefs.edit().putString(application.getString(R.string.key_wallets), value).commit()
            field = value
        }

    private var txList : String? = ""
        get() = prefs.getString(application.getString(R.string.key_tx_list), "")
        set(value) {
            prefs.edit().putString(application.getString(R.string.key_tx_list), value).commit()
            field = value
        }

    private fun initWallets() {
        walletManager.init(wallets, txList)
    }

    private fun createWallet(cryptoType : CryptoType, label : String) {
        wallets = walletManager.createWallet(cryptoType, label)
    }

    private fun restoreWallet(cryptoType : CryptoType, seed : String, label : String) {
       walletManager.restoreWallet(cryptoType, seed, label)
    }

    companion object {
        private lateinit var mAppData : AppData

        fun getLoginType() : LoginType {
            val ret = mAppData.logintype
            return LoginType.from(ret)
        }

        fun setLoginType(loginType : LoginType) {
            mAppData.logintype = loginType.ordinal
        }

        fun getUserIndex() : Int? {
            return mAppData.userIndex
        }

        fun setUserIndex(index: Int) {
            mAppData.userIndex = index
        }

        fun getLoginId() : String? {
            return mAppData.loginId
        }

        fun setLoginId(loginId : String) {
            mAppData.loginId = loginId
        }

        fun getLoginPw() : String? {
            return mAppData.loginPw
        }

        fun setLoginPw(loginPw : String) {
            mAppData.loginPw = loginPw
        }

        fun createWallet(cryptoType : CryptoType, label : String) {
            mAppData.createWallet(cryptoType, label)
        }

        fun restoreWallet(cryptoType : CryptoType, seed : String, label : String) {
            mAppData.restoreWallet(cryptoType, seed, label)
        }

        fun getHDWallets() : List<HDWalletData> {
            return mAppData.walletManager.wallets
        }

        fun getSendTXResultList(label : String) : List<SendTXResult>? {
            return mAppData.walletManager.transactions.get(label)
        }

        fun addSendTXResult(label : String, sendTXResult : SendTXResult) {
            if(getSendTXResultList(label) == null) {
                val list : MutableList<SendTXResult> = mutableListOf()
                list.add(sendTXResult)
                mAppData.walletManager.transactions.put(label, list)
            }
            else {
                mAppData.walletManager.transactions.get(label)?.add(sendTXResult)
            }
        }

        fun delSendTXResult(label : String, sendTXResult : SendTXResult) {
            val find : SendTXResult? = mAppData.walletManager.transactions.get(label)?.find { e -> e.hashtx.equals(sendTXResult.hashtx) }
            if(find != null) {
                mAppData.walletManager.transactions.get(label)?.remove(find)
            }
        }

        fun saveHDWallets() {
            mAppData.wallets = mAppData.walletManager.walletsToJson()
            mAppData.txList = mAppData.walletManager.txListToJson()
        }

        fun resetHDWallets() {
            mAppData.wallets = ""
            mAppData.txList = ""
            mAppData.initWallets()
        }

//        fun getTxList() : List<SendTXResult> {
//            return mAppData.walletManager.
//        }

//        fun getWallets() : String? {
//            return mAppData.wallets
//        }
//
//        fun delWallets() {
//            setWallets("")
//        }
    }
}
