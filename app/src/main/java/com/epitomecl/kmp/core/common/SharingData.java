package com.epitomecl.kmp.core.common;

import com.epitomecl.kmp.core.util.shamir.Scheme;

import info.blockchain.wallet.crypto.AESUtil;
import org.bitcoinj.core.ECKey;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.util.encoders.Hex;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SharingData {
    private static final int share = 3;
    private static final int need = 2;

    private Map<Integer, byte[]> parts;
    private String encrypted;

    public SharingData(String source) {
        final ECKey key = new ECKey();
        final byte[] encryptionKey = key.getPrivKeyBytes();

        try {
            final byte[] encrypt = AESUtil.encryptWithKey(key.getPrivKeyBytes(), source);
            encrypted = new String(encrypt);

            final String decrypt = AESUtil.decryptWithKey(encryptionKey, encrypted);
            if (!decrypt.equals(source)) {
                throw new Exception("SharingData mnemonics encryption fail.");
            }
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Scheme scheme = Scheme.of(share, need);
        parts = scheme.split(encryptionKey);
    }

    public SharingData(String encrypted, Map<Integer, byte[]> parts) throws Exception {
        this.encrypted = encrypted;
        this.parts = parts;

        if (parts.size() < need) {
            throw new Exception("Parts needs more part.");
        }
    }

    public Map<Integer, byte[]> getSharingParts() {
        return parts;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public String getJoinData() {
        byte[] encryptionKey = null;
        String decrypt = null;
        try {
            final Scheme scheme = Scheme.of(share, need);
            encryptionKey = scheme.join(parts);
            decrypt = AESUtil.decryptWithKey(encryptionKey, encrypted);
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypt;
    }
}
