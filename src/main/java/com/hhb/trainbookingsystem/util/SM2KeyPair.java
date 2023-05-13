package com.hhb.trainbookingsystem.util;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECPoint;


public class SM2KeyPair {
    /** 公钥 */
    private ECPoint publicKey;
    /** 私钥 */
    private BigInteger privateKey;

    SM2KeyPair(ECPoint publicKey, BigInteger privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public ECPoint getPublicKey() {
        return publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }
}
