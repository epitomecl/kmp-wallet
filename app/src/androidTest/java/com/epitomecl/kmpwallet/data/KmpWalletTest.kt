package com.epitomecl.kmpwallet.data

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.epitomecl.kmp.core.wallet.AccountData
import com.epitomecl.kmp.core.wallet.AccountKeyDerivation
import com.epitomecl.kmp.core.wallet.CryptoType
import com.epitomecl.kmp.core.wallet.HDWalletData
import com.epitomecl.kmpwallet.R
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.wallet.KeyChain
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters
import org.slf4j.LoggerFactory

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class KmpWalletTest {
    private val log = LoggerFactory.getLogger(KmpWalletTest::class.java)

    // Context of the app under test.
    internal var appContext = InstrumentationRegistry.getTargetContext()
    internal var appData = AppData(appContext, appContext.getSharedPreferences(appContext.getString(R.string.KMP), Context.MODE_PRIVATE))

    @Before
    internal fun setUp() {
        AppData.wipeData()
    }

    @Test
    fun test1createWallet() {
        val cryptoType : CryptoType = CryptoType.BITCOIN_TESTNET
        val label : String = "test-wallet-label"

        AppData.createWallet(cryptoType, label)

        assertEquals(AppData.getHDWallets().get(0).label, label)
    }

    @Test
    fun test2createAccount() {
        test1createWallet()

        val hdWalletData : HDWalletData = AppData.getHDWallets().get(0)
        val label : String = "test-account-label"

        val account : AccountData = hdWalletData.addAccount(label)

        assertEquals(account.label, label)
    }

    @Test
    fun test3ReceiveAddress() {
        val xpub : String = "tpubDDdUfUJ5n4T9A72TvxD1GnqLLRDfkw1YrCNFFzJdosnder8FL71yLPxPbixLHe1WDSm4tuNEJVmxFvAJmYSpQ7sPc3U9pym6xNRqEDMx6sb"
        val deriver  = AccountKeyDerivation(TestNet3Params.get(), xpub)

        val receiveAddress1 = deriver.getAddresses(KeyChain.KeyPurpose.RECEIVE_FUNDS)
        val receiveAddress2 = deriver.getAddresses(KeyChain.KeyPurpose.RECEIVE_FUNDS)
        val receiveAddress3 = deriver.getAddresses(KeyChain.KeyPurpose.RECEIVE_FUNDS)
        val receiveAddress4 = deriver.getAddresses(KeyChain.KeyPurpose.RECEIVE_FUNDS)
        val receiveAddress5 = deriver.getAddresses(KeyChain.KeyPurpose.RECEIVE_FUNDS)

        assertEquals(receiveAddress1, "mjGEDwym8a4nt8gGCAtcFvy2ptNpLHyiYy")
        assertEquals(receiveAddress2, "miG8YhhW7WV6QoxiiYC3vEuhaPAEsMGC9B")
        assertEquals(receiveAddress3, "n1GFG6VzdjpbECaAa1mv6bqRZxtpAerpyv")
        assertEquals(receiveAddress4, "mmsf65rZfa4jZu6R16orKxt2XNk3s7Laxr")
        assertEquals(receiveAddress5, "mr8TGwph632qsyqfegNFJYh2c8nshjk6ZL")
    }

    @Test
    fun test4ChangeAddress() {
        val xpub : String = "tpubDDdUfUJ5n4T9A72TvxD1GnqLLRDfkw1YrCNFFzJdosnder8FL71yLPxPbixLHe1WDSm4tuNEJVmxFvAJmYSpQ7sPc3U9pym6xNRqEDMx6sb"
        val deriver  = AccountKeyDerivation(TestNet3Params.get(), xpub)

        val changeAddress1 = deriver.getAddresses(KeyChain.KeyPurpose.CHANGE)
        val changeAddress2 = deriver.getAddresses(KeyChain.KeyPurpose.CHANGE)
        val changeAddress3 = deriver.getAddresses(KeyChain.KeyPurpose.CHANGE)
        val changeAddress4 = deriver.getAddresses(KeyChain.KeyPurpose.CHANGE)
        val changeAddress5 = deriver.getAddresses(KeyChain.KeyPurpose.CHANGE)

        assertEquals(changeAddress1, "mptFMhEbXSWZpdPoGKd6AnqmACPbuwn4kb")
        assertEquals(changeAddress2, "mmq65ZyZpDbPvjvPnSHcY4MyyMXAEekecf")
        assertEquals(changeAddress3, "n2vGXpfev322JbebiqfwJ6TfEYGWSzQSWB")
        assertEquals(changeAddress4, "mizd2PfNoqUo1uhXwozdS5gh8yUL6MVy2W")
        assertEquals(changeAddress5, "mkMZemwpcQ9BXaVQQnHJpspxU5nHYdEW7h")
    }

    @Test
    fun test6BackupWallet() {

    }

    @Test
    fun test7RestoreWallet() {

    }

    @Test
    fun test8BackupSecretSharing() {

    }

    @Test
    fun test9RestoreSecretSharing() {

    }

    @Test
    fun test10DeleteWallet() {

    }
}
