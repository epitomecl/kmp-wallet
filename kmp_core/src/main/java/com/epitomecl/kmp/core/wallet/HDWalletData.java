package com.epitomecl.kmp.core.wallet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.epitomecl.kmp.core.wallet.bip44.HDAccount;
import com.epitomecl.kmp.core.wallet.bip44.HDAddress;
import com.epitomecl.kmp.core.wallet.bip44.HDWallet;
import com.epitomecl.kmp.core.wallet.bip44.HDWalletFactory;
import org.apache.commons.codec.DecoderException;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.util.encoders.Hex;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        creatorVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class HDWalletData {

    private static final int DEFAULT_MNEMONIC_LENGTH = 12;
    private static final int DEFAULT_NEW_WALLET_SIZE = 1;
    private static final String DEFAULT_PASSPHRASE = "";

    @JsonProperty("label")
    private String label;

    @JsonProperty("crypto_type")
    private CryptoType cryptoType;

    @JsonProperty("accounts")
    private List<AccountData> accounts;

    @JsonProperty("seed_hex")
    private String seedHex;

    @JsonProperty("passphrase")
    private String passphrase;

    @JsonProperty("mnemonic_verified")
    private boolean mnemonicVerified;

    @JsonProperty("default_account_idx")
    private int defaultAccountIdx;

    //bip44 Wallet needed for address derivation
    private HDWallet HD;

    private NetworkParameters param;

    public final HDWallet getHDWallet() {
        return HD;
    }

    public final NetworkParameters getNetworkParameters() {
        return param;
    }

    private static NetworkParameters getNetworkParameters(CryptoType cryptoType) {
        NetworkParameters result = null;

        switch (cryptoType) {
            case BITCOIN:
            case ETHEREUM:          //Create etherium wallet code from <== EthereumWalletTest.java
            case ETHEREUM_TESTNET:
                result = NetworkParameters.prodNet();
                break;
            case BITCOIN_TESTNET:
                result = NetworkParameters.testNet3();
                break;
        }

        return result;
    }

//    public void decryptHDWallet(@Nullable String validatedSecondPassword, String sharedKey, int iterations)
//            throws IOException, Exception, InvalidCipherTextException, DecoderException,
//            MnemonicException.MnemonicLengthException, MnemonicException.MnemonicWordException, MnemonicException.MnemonicChecksumException, Exception {
//
//        if (HD == null) {
//            instantiateBip44Wallet();
//        }
//
//        if (validatedSecondPassword != null && !isBip44AlreadyDecrypted()) {
//
//            String encryptedSeedHex = getSeedHex();
//
//            String decryptedSeedHex = DoubleEncryptionFactory.decrypt(
//                    encryptedSeedHex, sharedKey, validatedSecondPassword,
//                    iterations);
//
//            HD = HDWalletFactory
//                    .restoreWallet(param,
//                            HDWalletFactory.Language.US,
//                            decryptedSeedHex,
//                            getPassphrase(),
//                            accounts.size());
//        }
//    }

    public void instantiateBip44Wallet()
            throws DecoderException, MnemonicException.MnemonicLengthException, MnemonicException.MnemonicWordException, MnemonicException.MnemonicChecksumException,
            IOException, Exception {

        try {
            param = getNetworkParameters(cryptoType);

            int walletSize = DEFAULT_NEW_WALLET_SIZE;
            if (accounts != null) walletSize = accounts.size();
            HD = HDWalletFactory
                    .restoreWallet(param, HDWalletFactory.Language.US,
                            getSeedHex(), getPassphrase(), walletSize);
        } catch (Exception e) {

            ArrayList<String> xpubList = new ArrayList<>();
            for (AccountData account : getAccounts()) {
                xpubList.add(account.getXpub());
            }

            HD = HDWalletFactory
                    .restoreWatchOnlyWallet(param,
                            xpubList);
        }

        if (HD == null) {
            throw new Exception("HD instantiation failed");
        }
    }

    private boolean isBip44AlreadyDecrypted() {

        return
                HD != null
                        && HD.getAccount(0).getXPriv() != null
                        && HD.getAccounts().size() == accounts.size();
    }

    private void validateHD() throws Exception {
        if (HD == null) {
            throw new Exception("HD wallet not instantiated");
        } else if (HD.getAccount(0).getXPriv() == null) {
            throw new Exception("Wallet private key unavailable. First decrypt with second password.");
        }
    }

    public HDWalletData() {
        //parameterless constructor needed for jackson
    }

    public HDWalletData(CryptoType cryptoType, String defaultAccountName) throws Exception {

        this.label = defaultAccountName;
        this.cryptoType = cryptoType;
        this.param = getNetworkParameters(cryptoType);

        this.HD = HDWalletFactory
                .createWallet(param, HDWalletFactory.Language.US,
                        DEFAULT_MNEMONIC_LENGTH, DEFAULT_PASSPHRASE, DEFAULT_NEW_WALLET_SIZE);

        List<HDAccount> hdAccounts = this.HD.getAccounts();
        List<AccountData> accountBodyList = new ArrayList<>();
        int accountNumber = 1;
        for (int i = 0; i < hdAccounts.size(); i++) {

            String label = defaultAccountName;
            if (accountNumber > 1) {
                label = defaultAccountName + " " + accountNumber;
            }

            HDAccount hdAccount = this.HD.getAccount(0);

            Cache cache = new Cache();
            cache.setReceiveAccount(hdAccount.getReceive().getAddressAt(0).getAddressBase58());
            cache.setChangeAccount(hdAccount.getChange().getAddressAt(0).getAddressBase58());

            AccountData accountBody = new AccountData();
            accountBody.setLabel(label);
            accountBody.setXpriv(hdAccount.getXPriv());
            accountBody.setXpub(hdAccount.getXpub());
            accountBody.setCache(cache);

            accountBodyList.add(accountBody);

            accountNumber++;
        }

        setSeedHex(this.HD.getSeedHex());
        setDefaultAccountIdx(0);
        setMnemonicVerified(false);
        setPassphrase(DEFAULT_PASSPHRASE);
        setAccounts(accountBodyList);
    }

    public String getLabel() {
        return label;
    }

    public CryptoType getCryptoType() {
        return cryptoType;
    }

    public List<AccountData> getAccounts() {
        return accounts;
    }

    public AccountData getAccount(int accountId) {
        return accounts.get(accountId);
    }

    public String getSeedHex() {
        return seedHex;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public boolean isMnemonicVerified() {
        return mnemonicVerified;
    }

    public int getDefaultAccountIdx() {
        return defaultAccountIdx;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCryptoType(CryptoType cryptoType) {
        this.cryptoType = cryptoType;
    }

    public void setAccounts(List<AccountData> accounts) {
        this.accounts = accounts;
    }

    public void setSeedHex(String seedHex) {
        this.seedHex = seedHex;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public void setMnemonicVerified(boolean mnemonicVerified) {
        this.mnemonicVerified = mnemonicVerified;
    }

    public void setDefaultAccountIdx(int defaultAccountIdx) {
        this.defaultAccountIdx = defaultAccountIdx;
    }

    public static HDWalletData fromJson(String json)
            throws IOException, MnemonicException.MnemonicWordException, DecoderException,
            MnemonicException.MnemonicChecksumException, MnemonicException.MnemonicLengthException, Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        HDWalletData hdWallet = mapper.readValue(json, HDWalletData.class);
        hdWallet.instantiateBip44Wallet();

        return hdWallet;
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    /**
     * @return Non-archived account xpubs
     */
    public List<String> getActiveXpubs() {

        ArrayList<String> xpubs = new ArrayList<>();

        if (getAccounts() == null) {
            return xpubs;
        }

        int nb_accounts = getAccounts().size();
        for (int i = 0; i < nb_accounts; i++) {

            AccountData account = getAccounts().get(i);
            boolean isArchived = account.isArchived();
            if (!isArchived) {
                String xpub = account.getXpub();
                if (xpub != null && xpub.length() > 0) {
                    xpubs.add(xpub);
                }
            }
        }
        return xpubs;
    }

    public AccountData addAccount(String label)
            throws
            Exception {

        if (HD == null) {
            throw new Exception("HD wallet not instantiated");
        }

        HDWallet bip44Wallet = HD;
        HDAccount hdAccount = bip44Wallet.addAccount();

        if (HD.getAccounts().get(0).getXPriv() == null) {
            throw new Exception("HD wallet not decrypted");
        }

        Cache cache = new Cache();
        cache.setReceiveAccount(hdAccount.getReceive().getAddressAt(0).getAddressBase58());
        cache.setChangeAccount(hdAccount.getChange().getAddressAt(0).getAddressBase58());

        AccountData accountBody = new AccountData();
        accountBody.setLabel(label);
        accountBody.setXpub(hdAccount.getXpub());
        accountBody.setXpriv(hdAccount.getXPriv());
        accountBody.setCache(cache);

        getAccounts().add(accountBody);

        return accountBody;
    }

    public AccountData addAccount(String label, String xpriv, String xpub) {

        AccountData accountBody = new AccountData();
        accountBody.setLabel(label);
        accountBody.setXpub(xpub);
        accountBody.setXpriv(xpriv);

        getAccounts().add(accountBody);

        return accountBody;
    }

    public static HDWalletData recoverFromMnemonic(CryptoType crytoType, String mnemonic, String defaultAccountName, IAPIManager api)
            throws Exception {
        return recoverFromMnemonic(crytoType, mnemonic, "", defaultAccountName, 0, api);
    }

    public static HDWalletData recoverFromMnemonic(CryptoType crytoType, String mnemonic, String defaultAccountName,
                                                   int accountSize, IAPIManager api) throws Exception {
        return recoverFromMnemonic(crytoType, mnemonic, "", defaultAccountName, accountSize, api);
    }

    public static HDWalletData recoverFromMnemonic(CryptoType crytoType, String mnemonic, String passphrase,
                                                   String defaultAccountName, IAPIManager api) throws Exception {
        return recoverFromMnemonic(crytoType, mnemonic, passphrase, defaultAccountName, 0, api);
    }

    public static HDWalletData recoverFromMnemonic(CryptoType cryptoType, String mnemonic, String passphrase,
                                                   String defaultAccountName, int walletSize, IAPIManager api) throws Exception {
        NetworkParameters param = getNetworkParameters(cryptoType);

        //Start with initial wallet size of 1.
        //After wallet is recovered we'll check how many accounts to restore
        HDWallet bip44Wallet = HDWalletFactory
                .restoreWallet(param, HDWalletFactory.Language.US,
                        mnemonic, passphrase, DEFAULT_NEW_WALLET_SIZE);

        HDWalletData hdWalletData = new HDWalletData();
        hdWalletData.setAccounts(new ArrayList<AccountData>());

        if (walletSize <= 0) {
            walletSize = getDeterminedSizeFromServer(1, 5, 0, bip44Wallet, api);
        }

        bip44Wallet = HDWalletFactory
                .restoreWallet(param, HDWalletFactory.Language.US,
                        mnemonic, passphrase, walletSize);

        //Set accounts
        int accountNumber = 1;
        for (HDAccount account : bip44Wallet.getAccounts()) {
            String xpub = account.getXpub();
            String xpriv = account.getXPriv();
            String label = defaultAccountName;
            if (accountNumber > 1) {
                label = defaultAccountName + " " + accountNumber;
            }

            hdWalletData.addAccount(label, xpriv, xpub);
            accountNumber++;
        }

        hdWalletData.setSeedHex(Hex.toHexString(bip44Wallet.getSeed()));
        hdWalletData.setPassphrase(bip44Wallet.getPassphrase());
        hdWalletData.setMnemonicVerified(false);
        hdWalletData.setDefaultAccountIdx(0);

        return hdWalletData;
    }

    public static HDWalletData restoreFromSeed(CryptoType cryptoType, String seedHex, String passphrase, String label, int accountNum, IAPIManager api)
            throws MnemonicException.MnemonicWordException, MnemonicException.MnemonicLengthException,
            IOException, Exception, MnemonicException.MnemonicChecksumException, DecoderException {
        NetworkParameters param = getNetworkParameters(cryptoType);

        HDWallet bip44Wallet;
        MnemonicCode mc;
        byte[] seed;

        InputStream wis = HDWalletFactory.class.getClassLoader().getResourceAsStream("wordlist/en_US.txt");
        if (wis == null) {
            throw new MnemonicException.MnemonicWordException("cannot read BIP39 word list");
        } else {
            mc = new MnemonicCode(wis, (String)null);
            seed = Hex.decode(seedHex);
            bip44Wallet = new HDWallet(mc, param, seed, passphrase, accountNum);
            wis.close();
        }

        HDWalletData hdWalletData = new HDWalletData();
        hdWalletData.setAccounts(new ArrayList<AccountData>());

        //Determine size of wallet accounts
        if (accountNum <= 0) {
            accountNum = getDeterminedSizeFromServer(1, 5, 0, bip44Wallet, api);
        }
        bip44Wallet = new HDWallet(mc, param, seed, passphrase, accountNum);

        //Set accounts
        int accountNumber = 1;
        for (HDAccount account : bip44Wallet.getAccounts()) {
            String xpub = account.getXpub();
            String xpriv = account.getXPriv();
            String accountlabel = label;
            if (accountNumber > 1) {
                accountlabel = label + " " + accountNumber;
            }

            Cache cache = new Cache();
            cache.setReceiveAccount(account.getReceive().getAddressAt(0).getAddressBase58());
            cache.setChangeAccount(account.getChange().getAddressAt(0).getAddressBase58());

            AccountData accont = hdWalletData.addAccount(accountlabel, xpriv, xpub);
            accont.setCache(cache);
            accountNumber++;
        }

        hdWalletData.setSeedHex(Hex.toHexString(bip44Wallet.getSeed()));
        hdWalletData.setPassphrase(bip44Wallet.getPassphrase());
        hdWalletData.setMnemonicVerified(false);
        hdWalletData.setDefaultAccountIdx(0);
        hdWalletData.setLabel(label);
        hdWalletData.setCryptoType(cryptoType);
        hdWalletData.instantiateBip44Wallet();

        return hdWalletData;
    }

    private static int getDeterminedSizeAddress() {

        return 0;
    }

    private static int getDeterminedSizeFromServer(int walletSize, int trySize, int currentGap, HDWallet bip44Wallet, IAPIManager api) {
        //Todo: determine size of wallet accounts from the result of request with the server

//        LinkedList<String> xpubs = new LinkedList<>();
        LinkedList<String> xpubs = new LinkedList<>();

        for (int i = 0; i < trySize; i++) {
            HDAccount account = bip44Wallet.addAccount();
            xpubs.add(account.getXpub());
        }

//        Response<HashMap<String, Balance>> exe = blockExplorer
//                .getBalance(xpubs, FilterType.RemoveUnspendable).execute();
//
//        if (!exe.isSuccessful()) {
//            throw new Exception(exe.code() + " " + exe.errorBody().string());
//        }

//        HashMap<String, Balance> map = exe.body();

        HashMap<String, Integer> map = new HashMap<>();
        for (String xpub : xpubs) {
            if(api != null) {
                int s = api.spendTXOCount("");
                map.put(xpub, s);
            }
        }

        final int lookAheadTotal = 10;
        for (String xpub : xpubs) {

            //If account has txs
            //if (map.get(xpub).getTxCount() > 0L) {
            if (map.get(xpub) > 0L) {
                walletSize++;
                currentGap = 0;
            } else {
                currentGap++;
            }

            if (currentGap >= lookAheadTotal) {
                return walletSize;
            }
        }

        return getDeterminedSizeFromServer(walletSize, trySize * 2, currentGap, bip44Wallet, api);
    }

//    public static boolean hasTransactions(BlockExplorer blockExplorer, String xpub)
//            throws Exception {
//
//        Response<HashMap<String, Balance>> exe = blockExplorer
//                .getBalance(Arrays.asList(xpub), FilterType.RemoveUnspendable).execute();
//
//        if (!exe.isSuccessful()) {
//            throw new Exception(exe.code() + " " + exe.errorBody().string());
//        }
//
//        HashMap<String, Balance> body = exe.body();
//
//        return body.get(xpub).getTxCount() > 0L;
//    }

//    public List<ECKey> getHDKeysForSigning(AccountData account, SpendableUnspentOutputs unspentOutputBundle)
//            throws Exception {
//
//        validateHD();
//
//        List<ECKey> keys = new ArrayList<>();
//
//        HDAccount hdAccount = getHDAccountFromAccountBody(account);
//        if (hdAccount != null) {
//            for (UnspentOutput unspent : unspentOutputBundle.getSpendableOutputs()) {
//                if (unspent.getXpub() != null) {
//                    String[] split = unspent.getXpub().getPath().split("/");
//                    int chain = Integer.parseInt(split[1]);
//                    int addressIndex = Integer.parseInt(split[2]);
//
//                    HDAddress hdAddress = hdAccount.getChain(chain).getAddressAt(addressIndex);
//                    ECKey walletKey = new PrivateKeyFactory()
//                            .getKey(PrivateKeyFactory.WIF_COMPRESSED, hdAddress.getPrivateKeyString());
//                    keys.add(walletKey);
//                }
//            }
//        }
//
//        return keys;
//    }

    public HDAccount getHDAccountFromAccountBody(AccountData accountBody) throws Exception {

        if (HD == null) {
            throw new Exception("HD wallet not instantiated");
        }

        for (HDAccount account : HD.getAccounts()) {
            if (account.getXpub().equals(accountBody.getXpub())) {
                return account;
            }
        }
        return null;
    }

    //no need for second pw. only using HD xpubs
    // TODO: 16/02/2017 Old. Investigate better way to do this
    public BiMap<String, Integer> getXpubToAccountIndexMap() throws Exception {

        if (HD == null) {
            throw new Exception("HD wallet not instantiated");
        }

        BiMap<String, Integer> xpubToAccountIndexMap = HashBiMap.create();

        List<HDAccount> accountList = HD.getAccounts();

        for (HDAccount account : accountList) {
            xpubToAccountIndexMap.put(account.getXpub(), account.getId());
        }

        return xpubToAccountIndexMap;
    }

    // TODO: 16/02/2017 Old. Investigate better way to do this
    public Map<Integer, String> getAccountIndexToXpubMap() throws Exception {
        return getXpubToAccountIndexMap().inverse();
    }

    /**
     * Bip44 master private key. Not to be confused with bci HDWallet seed
     *
     * @return
     */
    public DeterministicKey getMasterKey() throws Exception {

        validateHD();
        return HD.getMasterKey();
    }

    public List<String> getMnemonic() throws Exception {

        validateHD();
        return HD.getMnemonic();
    }

    @Nullable
    public String getLabelFromXpub(String xpub) {
        List<AccountData> accounts = getAccounts();

        for (AccountData account : accounts) {
            if (account.getXpub().equals(xpub)) {
                return account.getLabel();
            }
        }

        return null;
    }
}
