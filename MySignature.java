/**
 * INF1416 - Segurança da Informação - Trabalho 2: MySignature
 * Prof.: Anderson Oliveira da Silva 
 * 
 * Gustavo Barros Marchesan - 1521500
 * Daniela Brazão Maksoud - 2111121
 * 
 * Para compilar o pacote:
 * javac -d .  MySignature.java
 * 
 * 
 */

package mysignature;

import java.security.*;
import javax.crypto.*;
import java.util.*;

public class MySignature {
    private static MySignature signature = new MySignature();
    private Cipher cipher;
    private MessageDigest messageDigest;

    // [1. MD5withRSA, 2. SHA1withRSA, 3. SHA256withRSA, 4.SHA512withRSA]
    public static MySignature getInstance(String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException {
        String[] alg = algorithm.split("with");
        String hash = alg[0];
        String key = alg[1];

        signature.messageDigest = MessageDigest.getInstance(hash);
        signature.cipher = Cipher.getInstance(key);

        return signature;
    } 

    public final void initSign(PrivateKey privateKey) throws InvalidKeyException {
        this.cipher.init(Cipher.ENCRYPT_MODE, privateKey);
    } 

    public final void update(byte b) {
        this.messageDigest.update(b);
    }

    public final byte[] sign() throws IllegalBlockSizeException, BadPaddingException {
        byte[] md = this.messageDigest.digest();
		return this.cipher.doFinal(md);
    }

    public final void initVerify(PublicKey publicKey) throws InvalidKeyException {
        this.cipher.init(Cipher.DECRYPT_MODE, publicKey);
    }
    
    public final boolean verify(byte[] signature) throws IllegalBlockSizeException, BadPaddingException {
		return Arrays.equals(this.messageDigest.digest(), this.cipher.doFinal(signature));
    }
}
