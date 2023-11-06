package kryptotrainer;

import mybiginteger.*;

import java.util.Random;

/**
 * <p>Title: KryptoTrainer</p>
 * <p>Description: Übungsumgebung für das Wahlfach Kryptologie</p>
 * <p>Copyright: Copyright (c) 2006 / Samuel Beer</p>
 * <p>Company: Zürcher Hochschule Winterthur</p>
 * @author Samuel Beer
 * @version 1.0
 */

public class ElGamal {

  int bitLengthPublicKey;          // Länge der Primzahl p in Bits

  BigInteger[] publicKeyAlice;     // Öffentlicher Schlüssel (p,g,A) von Alice

  BigInteger privateKeyAlice;      // Privater Schlüssel a von Alice

  BigInteger plainText;            // Klartext Bob -> Alice

  BigInteger[] cipheredText;       // Chiffrat (B,c) Bob -> Alice

  BigInteger decipheredText;       // Dechiffrierter Text Bob -> Alice


  /************************************************************************
   ************************************************************************
   * Methoden, die ausprogrammiert werden müssen.
   ************************************************************************
   ************************************************************************/

  /**
   * Öffentlichen Schlüssel (p,g,A) und privaten Schlüssel (a) für Alice
   * generieren und in publicKeyAlice bzw. privateKeyAlice speichern.
   */
  public void generateKeyPair() {
    BigInteger p = BigInteger.myProbableSafePrime(bitLengthPublicKey, 10, new Random());
    BigInteger g = BigInteger.valueOf(2);

    // Generate Alice's private key a (random integer in [1, p-2])
    privateKeyAlice = BigInteger.myProbableSafePrime(bitLengthPublicKey, 10, new Random());
    privateKeyAlice = privateKeyAlice.mod(p.subtract(BigInteger.valueOf(2))).add(BigInteger.ONE);

    // Calculate Alice's public key A = g^a mod p
    BigInteger A = g.modPow(privateKeyAlice, p);

    publicKeyAlice = new BigInteger[] { p, g, A };
  }

  /**
   * Chiffrat (B,c) Bob -> Alice erstellen und in cipheredText abspeichern.
   */
  public void createCipheredText() {
    BigInteger p = publicKeyAlice[0];
    BigInteger g = publicKeyAlice[1];

    BigInteger k = BigInteger.myProbableSafePrime(bitLengthPublicKey, 10, new Random());
    k = k.mod(p.subtract(BigInteger.valueOf(2))).add(BigInteger.ONE);

    // Calculate B = g^k mod p
    BigInteger ciphertextB = g.modPow(k, p);

    // Calculate c = (plaintext * B^a) mod p
    BigInteger c = plainText.multiply(ciphertextB.modPow(privateKeyAlice, p)).mod(p);

    cipheredText = new BigInteger[] { ciphertextB, c };
  }

  /**
   * Dechiffrierten Text Bob -> Alice erstellen und in decipheredText abspeichern.
   */
  public void createDecipheredText() {
    BigInteger ciphertextB = cipheredText[0];
    BigInteger c = cipheredText[1];
    BigInteger p = publicKeyAlice[0];

    // Calculate the shared secret S = B^a mod p
    BigInteger S = ciphertextB.modPow(privateKeyAlice, p);

    // Calculate the inverse of S
    BigInteger SInverse = S.modInverse(p);

    // Calculate the plaintext = c * SInverse mod p
    decipheredText = c.multiply(SInverse).mod(p);
  }


  /************************************************************************
   ************************************************************************
   * Methoden, die fertig vorgegeben sind.
   ************************************************************************
   ************************************************************************/

  public ElGamal() {
  }

  public void setBitLength(int len) {
    bitLengthPublicKey = len;
  }

  public void setPlainText(BigInteger plain) {
    plainText = plain;
  }

  public BigInteger[] getCipheredText() {
    return cipheredText;
  }

  public BigInteger getDecipheredText() {
    return decipheredText;
  }

  public BigInteger[] getPublicKey() {
    return publicKeyAlice;
  }

  public BigInteger getPrivateKey() {
    return privateKeyAlice;
  }
}
