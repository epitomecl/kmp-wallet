package com.epitomecl.kmp.core.wallet;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.wallet.KeyChain;

public class AccountKeyDerivation {

    private NetworkParameters params;
    private DeterministicKey accountKey;
    private ChildKeyNode receive;
    private ChildKeyNode change;

    public AccountKeyDerivation(NetworkParameters params, String xpub){
        this.params = params;
        this.accountKey = DeterministicKey.deserializeB58(xpub, params);
        this.receive = new ChildKeyNode(HDKeyDerivation.deriveChildKey(accountKey,0));
        this.change = new ChildKeyNode(HDKeyDerivation.deriveChildKey(accountKey,1));
    }

    public String getAddresses(KeyChain.KeyPurpose purpose) {
        DeterministicKey key = getKey(purpose);
        String address = key.toAddress(params).toBase58();
        return address;
    }

    public DeterministicKey getKey(KeyChain.KeyPurpose purpose) {
        DeterministicKey key = getChildKeyNode(purpose).getChildKey();
        return key;
    }

    public ChildKeyNode getChildKeyNode(KeyChain.KeyPurpose purpose) {
        ChildKeyNode result;

        switch(purpose) {
            case RECEIVE_FUNDS:
                result = receive;
                break;
            case CHANGE:
                result = change;
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return result;
    }

    public class ChildKeyNode {
        private DeterministicKey key;
        private int child;
        private int gap;

        public ChildKeyNode(DeterministicKey key) {
            this.key = key;
            child = 0;
            gap = 0;
        }

        public DeterministicKey getChildKey() {
            DeterministicKey result = HDKeyDerivation.deriveChildKey(key, child);
            child++;
            return result;
        }

        public void addGap() {
            gap++;
        }

        public int getGap() {
            return gap;
        }

        public void resetGap() {
            gap = 0;
        }
    }
}
