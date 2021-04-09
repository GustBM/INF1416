public class MySignature {
    public static Signature getInstance(String algorithm) {} 
    public final void initSign(PrivateKey privateKey) {} 
    public final void update(byte b) {}
    public final byte[] sign() {}
    public final void initVerify(PublicKey publicKey) {}
    public final boolean verify(byte[] signature) {}
}