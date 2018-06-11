package com.epitomecl.kmpwallet.data

import android.app.Application
import android.content.SharedPreferences
import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.R
import com.epitomecl.kmpwallet.util.SharedPreferenceSecure

class AppData(val application: Application, val sharedPreferences : SharedPreferences) {

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

    private fun initWallets() {
        walletManager.init(wallets)
    }

    private fun createWallet(cryptoType : CryptoType, label : String) {
        wallets = walletManager.createWallet(cryptoType, label)
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

        fun getHDWallets() : List<HDWalletData> {
            return mAppData.walletManager.wallets
        }

//        fun getWallets() : String? {
//            return mAppData.wallets
//        }
//
//        fun delWallets() {
//            setWallets("")
//        }

    }
}
