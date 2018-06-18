package info.blockchain.wallet.api;

import info.blockchain.wallet.BlockchainFramework;
import info.blockchain.wallet.api.data.Settings;
import info.blockchain.wallet.api.data.Status;
import info.blockchain.wallet.api.data.WalletOptions;

import org.apache.commons.lang3.StringUtils;
import org.spongycastle.util.encoders.Hex;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
public class WalletApi {

    private static WalletEndpoints walletApi;
    private static WalletEndpoints walletServer;

    private WalletEndpoints getApiInstance() {
        if (walletApi == null) {
            walletApi = BlockchainFramework.getRetrofitApiInstance().
                    create(WalletEndpoints.class);
        }
        return walletApi;
    }

    private WalletEndpoints getExplorerInstance() {
        if (walletServer == null) {
            walletServer = BlockchainFramework.getRetrofitExplorerInstance()
                    .create(WalletEndpoints.class);
        }
        return walletServer;
    }

    public Call<ResponseBody> getRandomBytesCall() {
        return getApiInstance().getRandomBytesCall(32, "hex");
    }

    public Observable<ResponseBody> getRandomBytes() {
        return getApiInstance().getRandomBytes(32, "hex");
    }

    public Observable<ResponseBody> updateFirebaseNotificationToken(String token,
                                                                    String guid,
                                                                    String sharedKey) {

        return getExplorerInstance().postToWallet("update-firebase",
                guid,
                sharedKey,
                token,
                token.length(),
                BlockchainFramework.getApiCode());
    }

    public Observable<ResponseBody> registerMdid(String guid,
                                                 String sharedKey,
                                                 String signedGuid) {

        return getExplorerInstance().postToWallet("register-mdid",
                guid, sharedKey, signedGuid, signedGuid.length(),
                BlockchainFramework.getApiCode());
    }

    public Observable<ResponseBody> unregisterMdid(String guid,
                                                   String sharedKey,
                                                   String signedGuid) {

        return getExplorerInstance().postToWallet("unregister-mdid",
                guid, sharedKey, signedGuid, signedGuid.length(),
                BlockchainFramework.getApiCode());
    }

    public Observable<Response<Status>> setAccess(String key, String value, String pin) {
        String hex = Hex.toHexString(value.getBytes());
        return getExplorerInstance().pinStore(key, pin, hex, "put", BlockchainFramework.getApiCode());
    }

    public Observable<Response<Status>> validateAccess(String key, String pin) {
        return getExplorerInstance().pinStore(key, pin, null, "get", BlockchainFramework.getApiCode());
    }

    public Call<ResponseBody> insertWallet(String guid,
                                           String sharedKey,
                                           @Nullable List<String> activeAddressList,
                                           String encryptedPayload,
                                           String newChecksum,
                                           String email,
                                           String device) throws UnsupportedEncodingException {

        String pipedAddresses = null;
        if (activeAddressList != null) {
            pipedAddresses = StringUtils.join(activeAddressList, "|");
        }

        return getExplorerInstance().syncWalletCall("insert",
                guid,
                sharedKey,
                encryptedPayload,
                encryptedPayload.length(),
                URLEncoder.encode(newChecksum, "utf-8"),
                pipedAddresses,
                email,
                device,
                null,
                BlockchainFramework.getApiCode());
    }

    public Call<ResponseBody> updateWallet(String guid,
                                           String sharedKey,
                                           @Nullable List<String> activeAddressList,
                                           String encryptedPayload,
                                           String newChecksum,
                                           String oldChecksum,
                                           String device) throws UnsupportedEncodingException {

        String pipedAddresses = null;
        if (activeAddressList != null) {
            pipedAddresses = StringUtils.join(activeAddressList, "|");
        }

        return getExplorerInstance().syncWalletCall("update",
                guid,
                sharedKey,
                encryptedPayload,
                encryptedPayload.length(),
                URLEncoder.encode(newChecksum, "utf-8"),
                pipedAddresses,
                null,
                device,
                oldChecksum,
                BlockchainFramework.getApiCode());
    }

    public Call<ResponseBody> fetchWalletData(String guid, String sharedKey) {
        return getExplorerInstance().fetchWalletData("wallet.aes.json",
                guid,
                sharedKey,
                "json",
                BlockchainFramework.getApiCode());
    }

    public Observable<ResponseBody> submitTwoFactorCode(String sessionId, String guid, String twoFactorCode) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + sessionId);
        return getExplorerInstance().submitTwoFactorCode(
                headerMap,
                "get-wallet",
                guid,
                twoFactorCode,
                twoFactorCode.length(),
                "plain",
                BlockchainFramework.getApiCode());
    }

    public Observable<Response<ResponseBody>> getSessionId(String guid) {
        return getExplorerInstance().getSessionId(guid);
    }

    public Observable<Response<ResponseBody>> fetchEncryptedPayload(String guid, String sessionId) {
        return getExplorerInstance().fetchEncryptedPayload(guid,
                "SID=" + sessionId,
                "json",
                false,
                BlockchainFramework.getApiCode());
    }

    public Call<ResponseBody> fetchPairingEncryptionPasswordCall(final String guid) {
        return getExplorerInstance().fetchPairingEncryptionPasswordCall("pairing-encryption-password",
                guid,
                BlockchainFramework.getApiCode());
    }

    public Observable<ResponseBody> fetchPairingEncryptionPassword(final String guid) {
        return getExplorerInstance().fetchPairingEncryptionPassword("pairing-encryption-password",
                guid,
                BlockchainFramework.getApiCode());
    }

    public Observable<Settings> fetchSettings(String method, String guid, String sharedKey) {
        return getExplorerInstance().fetchSettings(method,
                guid,
                sharedKey,
                "plain",
                BlockchainFramework.getApiCode());
    }

    public Observable<ResponseBody> updateSettings(String method, String guid, String sharedKey, String payload) {
        return getExplorerInstance().updateSettings(method,
                guid,
                sharedKey,
                payload,
                payload.length(),
                "plain",
                BlockchainFramework.getApiCode());
    }

    public Observable<Status> logEvent(String name) {
        return getExplorerInstance().logEvent(
                name,
                BlockchainFramework.getApiCode());
    }

    public Observable<WalletOptions> getWalletOptions() {
        return getExplorerInstance().getWalletOptions(BlockchainFramework.getApiCode());
    }
}