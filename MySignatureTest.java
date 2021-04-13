/**
 * INF1416 - Segurança da Informação - Trabalho 2: MySignature
 * Prof.: Anderson Oliveira da Silva 
 * 
 * Gustavo Barros Marchesan - 1521500
 * Daniela Brazão Maksoud - 2111121
 * 
 */

import java.security.*;
import javax.crypto.*;
import mysignature.MySignature;

public class MySignatureTest {

  public static void main (String[] args) throws Exception {
  
    // Verifica args e recebe o texto plano
    if (args.length != 2) {
      System.err.println("Usage: java MySignatureTest <text> <algorithm_number> = [1. MD5withRSA, 2. SHA1withRSA, 3. SHA256withRSA, 4.SHA512withRSA]");
      System.exit(1);
    }

    byte[] plainText = args[0].getBytes("UTF8");
    int numAlgorithm = Integer.parseInt(args[1]);
    String nameAlgorithm = "";

    switch (numAlgorithm) {
        case 1:
          nameAlgorithm = "MD5withRSA";
          break;
        case 2:
          nameAlgorithm = "SHA1withRSA";
          break;
        case 3:
          nameAlgorithm = "SHA256withRSA";
          break;
        case 4:
          nameAlgorithm = "SHA512withRSA";
          break;
        default:
          System.err.println("Número do Algoritmo Inválido.");
          System.exit(1);
    }

    System.out.println( "\nPadrão de Assinatura: ");
    System.out.println(nameAlgorithm);
    
    // Gera o par de chaves RSA.
    System.out.println( "\nStart generating RSA key..." );
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    keyGen.initialize(2048);
    KeyPair key = keyGen.generateKeyPair();
    System.out.println( "Finished generating RSA key." );
    
    // Define um objeto signature para utilizar SHA1 e RSA
    // e assina o texto plano com a chave privada.
    // O provider utilizado tambem é impresso.
    MySignature sig = MySignature.getInstance(nameAlgorithm);
    sig.initSign(key.getPrivate());
    sig.update(plainText);
    byte[] signature = sig.sign();
    // System.out.println( sig.getProvider().getInfo() );
    System.out.println( "\nSignature:" );

    // Converte o signature para hexadecimal.
    StringBuffer buf = new StringBuffer();
    for(int i = 0; i < signature.length; i++) {
       String hex = Integer.toHexString(0x0100 + (signature[i] & 0x00FF)).substring(1);
       buf.append((hex.length() < 2 ? "0" : "") + hex);
    }

    // Imprime o signature em hexadecimal.
    System.out.println( buf.toString() );

    // Verifica a assinatura com a chave pública.
    System.out.println( "\nStart signature verification..." );
    sig.initVerify(key.getPublic());
    sig.update(plainText);
    try {
      if (sig.verify(signature)) {
        System.out.println( "Signature verified.\n" );
      } 
      else 
      {
        System.out.println( "Failed to verify signature." );
        throw new SignatureException("Signature Exception.");
      }
    } catch (SignatureException se) {
      System.out.println( "Signature failed" );
    }
  }
}
