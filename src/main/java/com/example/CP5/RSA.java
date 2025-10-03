package com.example.CP5;

import java.math.BigInteger;

public class RSA {

    private BigInteger p, q, n, phi, e, d;

    public RSA(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
        this.n = p.multiply(q);
        this.phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        this.e = generatePublicExponent(this.phi);
        this.d = e.modInverse(this.phi);
    }

    private BigInteger generatePublicExponent(BigInteger phi) {
        BigInteger e = new BigInteger("65537");
        while (phi.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        return e;
    }

    public BigInteger encrypt(byte[] message, BigInteger keyE, BigInteger keyN) {
        BigInteger messageInt = new BigInteger(1, message);
        return messageInt.modPow(keyE, keyN);
    }

    public byte[] decrypt(BigInteger encryptedMessage) {
        return encryptedMessage.modPow(this.d, this.n).toByteArray();
    }

    public BigInteger getPublicKeyE() { return e; }
    public BigInteger getN() { return n; }

    @Override
    public String toString() {
        return "RSA Keys {\n" +
                "  p=" + p + ",\n" +
                "  q=" + q + ",\n" +
                "  n=" + n + ",\n" +
                "  phi=" + phi + ",\n" +
                "  Public Key (e)=" + e + ",\n" +
                "  Private Key (d)=" + d + "\n" +
                '}';
    }
}