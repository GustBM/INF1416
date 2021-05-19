package service;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import model.User;

public class CertKeyTools {
	public static PrivateKey generatePrivateKey(String frase, String path, String userEmail) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException {
		SecureRandom prngSecurity = SecureRandom.getInstance("SHA1PRNG");
        byte[] secretWordBytes = frase.getBytes();
        prngSecurity.setSeed(secretWordBytes);
        
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(prngSecurity);
        Key key = keyGenerator.generateKey();

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        
        Path keyPath = Paths.get(path);
        byte[] plainText = null;
        
        try{
            plainText = cipher.doFinal(Files.readAllBytes(keyPath));
        } catch (Exception e){
        	dbConnect.register(4005, userEmail, "");
        }

        String userKey64 = new String(plainText, "UTF8");

        String[] userKey64Array = Arrays.copyOfRange(userKey64.split("\n"),1,userKey64.split("\n").length -1);
        String privateKey64 = Arrays.toString(userKey64Array);
        
        privateKey64 = privateKey64.substring(1,privateKey64.length() - 1);
        byte[] userKey64Encoded = Base64.getMimeDecoder().decode(privateKey64);

        PKCS8EncodedKeySpec encoded = new PKCS8EncodedKeySpec(userKey64Encoded);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(encoded);
	}
	
	public static PublicKey generatePublicKeyFromCertificate(User user) throws CertificateException {
		String certicateText = "";
		String resultText = "";
		boolean certificate64 = false;
		
		PreparedStatement ps;
    	ResultSet rs;
    	String query = "SELECT u_certificado FROM `usuarios` WHERE `u_email` =?";
    	
    	try {
    		ps = dbConnect.connectDB().prepareStatement(query);
            ps.setString(1, user.getEmail());
            rs = ps.executeQuery();
            
            if(rs.next())
            	certicateText = rs.getString("u_certificado");
            String[] certificateArray = certicateText.split("\n");
            for(String certificateLine : certificateArray) {
            	if(certificateLine.equals("-----BEGIN CERTIFICATE-----")) {
            		certificate64 = true;
            		resultText+= certificateLine+"\n";
            	}
            	
            	if(certificate64) {
            		resultText+= certificateLine+"\n";
            		continue;
            	}
            }
            
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream bytes = new ByteArrayInputStream(resultText.getBytes());
            X509Certificate certificate = (X509Certificate)certificateFactory.generateCertificate(bytes);
            return certificate.getPublicKey();
            
    	}catch (SQLException  ex) {
    		System.out.println(ex.getMessage());
        }
    	
    	return null;
	}
	
	
	
}
