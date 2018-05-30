package com.epitomecl.kmpwallet.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.provider.Settings
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.epitomecl.kmpwallet.BuildConfig
import com.epitomecl.kmpwallet.R

import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.PBEParameterSpec
import javax.inject.Singleton

@Singleton
class SharedPreferenceSecure(protected var context: Context, protected var delegate: SharedPreferences) : SharedPreferences {
    protected val UTF8: String
    private val SEKRIT: CharArray

    init {
        UTF8 = context.getString(R.string.utf8_small)
        SEKRIT = BuildConfig.SEKRIT.toCharArray()
    }

    inner class Editor : SharedPreferences.Editor {

        protected var delegate: SharedPreferences.Editor

        init {
            this.delegate = this@SharedPreferenceSecure.delegate.edit()
        }

        override fun putBoolean(key: String, value: Boolean): Editor {
            delegate.putString(key, encrypt(java.lang.Boolean.toString(value)))
            return this
        }

        override fun putFloat(key: String, value: Float): Editor {
            delegate.putString(key, encrypt(java.lang.Float.toString(value)))
            return this
        }

        override fun putInt(key: String, value: Int): Editor {
            delegate.putString(key, encrypt(Integer.toString(value)))
            return this
        }

        override fun putLong(key: String, value: Long): Editor {
            delegate.putString(key, encrypt(java.lang.Long.toString(value)))
            return this
        }

        override fun putString(key: String, value: String?): Editor {
            delegate.putString(key, encrypt(value))
            return this
        }

        override fun apply() {
            delegate.apply()
        }

        override fun clear(): Editor {
            delegate.clear()
            return this
        }

        override fun commit(): Boolean {
            return delegate.commit()
        }

        override fun remove(s: String): Editor {
            delegate.remove(s)
            return this
        }

        override fun putStringSet(
                arg0: String, arg1: Set<String>?): android.content.SharedPreferences.Editor? {
            // TODO Auto-generated method stub
            return null
        }
    }

    override fun edit(): Editor {
        return Editor()
    }

    override fun getAll(): Map<String, *> {
        throw UnsupportedOperationException() // left as an exercise to the
        // reader
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        val v = delegate.getString(key, null)
        return if (v != null) java.lang.Boolean.parseBoolean(decrypt(v)) else defValue
    }

    override fun getFloat(key: String, defValue: Float): Float {
        val v = delegate.getString(key, null)
        return if (v != null) java.lang.Float.parseFloat(decrypt(v)) else defValue
    }

    override fun getInt(key: String, defValue: Int): Int {
        val v = delegate.getString(key, null)
        return if (v != null) Integer.parseInt(decrypt(v)) else defValue
    }

    override fun getLong(key: String, defValue: Long): Long {
        val v = delegate.getString(key, null)
        return if (v != null) java.lang.Long.parseLong(decrypt(v)) else defValue
    }

    override fun getString(key: String, defValue: String?): String? {
        val v = delegate.getString(key, null)
        return if (v != null) decrypt(v) else defValue
    }

    override fun contains(s: String): Boolean {
        return delegate.contains(s)
    }

    override fun registerOnSharedPreferenceChangeListener(
            onSharedPreferenceChangeListener: OnSharedPreferenceChangeListener) {
        delegate.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(
            onSharedPreferenceChangeListener: OnSharedPreferenceChangeListener) {
        delegate.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    protected fun encrypt(value: String?): String {

        try {
            val bytes = value?.toByteArray(charset(UTF8)) ?: ByteArray(0)
            val keyFactory = SecretKeyFactory
                    .getInstance(context.getString(R.string.pbewithmd5anddes))
            val key = keyFactory.generateSecret(PBEKeySpec(SEKRIT))
            val pbeCipher = Cipher.getInstance(context.getString(R.string.pbewithmd5anddes))
            pbeCipher.init(
                    Cipher.ENCRYPT_MODE,
                    key,
                    PBEParameterSpec(Settings.Secure.getString(
                            context.getContentResolver(),
                            Settings.Secure.ANDROID_ID).toByteArray(charset(UTF8)), 20))
            return String(Base64.encode(pbeCipher.doFinal(bytes),
                    Base64.NO_WRAP), charset(UTF8))

        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    protected fun decrypt(value: String?): String {
        try {
            val bytes = if (value != null)
                Base64.decode(value,
                        Base64.DEFAULT)
            else
                ByteArray(0)
            val keyFactory = SecretKeyFactory
                    .getInstance(context.getString(R.string.pbewithmd5anddes))
            val key = keyFactory.generateSecret(PBEKeySpec(SEKRIT))
            val pbeCipher = Cipher.getInstance(context.getString(R.string.pbewithmd5anddes))
            pbeCipher.init(
                    Cipher.DECRYPT_MODE,
                    key,
                    PBEParameterSpec(Settings.Secure.getString(
                            context.getContentResolver(),
                            Settings.Secure.ANDROID_ID).toByteArray(charset(UTF8)), 20))
            return String(pbeCipher.doFinal(bytes), charset(UTF8))

        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    override fun getStringSet(arg0: String, arg1: Set<String>?): Set<String>? {
        return null
    }

}