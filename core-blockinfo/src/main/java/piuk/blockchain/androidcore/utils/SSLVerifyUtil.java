package piuk.blockchain.androidcore.utils;

import java.io.IOException;

import javax.net.ssl.SSLPeerUnverifiedException;

import io.reactivex.schedulers.Schedulers;
import piuk.blockchain.androidcore.data.api.ConnectionApi;
import piuk.blockchain.androidcore.data.rxjava.RxBus;
import piuk.blockchain.androidcore.data.rxjava.RxPinning;
import piuk.blockchain.androidcore.utils.rxjava.IgnorableDefaultObserver;


/**
 * Certificates to be pinned are derived via <code>openssl s_client -connect api.blockchain.info:443
 * | openssl x509 -pubkey -noout | openssl rsa -pubin -outform der | openssl dgst -sha256 -binary |
 * openssl enc -base64</code>, which returns a SHA-256 hash in Base64.
 */
public class SSLVerifyUtil {

    private final RxPinning rxPinning;
    private ConnectionApi connectionApi;

    public SSLVerifyUtil(RxBus rxBus, ConnectionApi connectionApi) {
        this.connectionApi = connectionApi;
        rxPinning = new RxPinning(rxBus);
    }

    /**
     * Pings the Explorer to check for a connection. If the call returns an {@link
     * IOException} or {@link SSLPeerUnverifiedException}, the {@link
     * RxPinning} object will broadcast this to the BaseAuthActivity
     * which will handle the response appropriately.
     */
    public void validateSSL() {
        rxPinning.call(() -> connectionApi.getExplorerConnection())
                .subscribeOn(Schedulers.io())
                .subscribe(new IgnorableDefaultObserver<>());
    }

}