package service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import model.User;

public class AuthenticationService {
	
	private static AuthenticationService authService = new AuthenticationService();
	
	private User user;
	private static PrivateKey privateKey;
	private static PublicKey publicKey;
	
	public static AuthenticationService getInstance() {
		return authService;
	}
	
	public User getUser() {
        return this.user;
    }
	
	public PrivateKey returnPriK () {
		return this.privateKey;
	}
	
	public PublicKey returnPubK () {
		return this.publicKey;
	}
	
	public boolean checkUserEmail(String username, boolean setUser) {
    	PreparedStatement ps;
    	ResultSet rs;
    	boolean checkUser = false;
    	String query = "SELECT * FROM `usuarios` WHERE `u_email` =?";
    	
    	try {
    		ps = dbConnect.connectDB().prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                checkUser = true;
                if(setUser) setUser(rs);
            }
    	}catch (SQLException  ex) {
    		System.out.println(ex.getMessage());
        }
    	
    	return checkUser;
    }
	
	public boolean isUserBlocked(String username) {
    	PreparedStatement ps;
    	ResultSet rs;
    	String query = "SELECT u_bloqueio FROM `usuarios` WHERE `u_email` =?";
    	
    	try {
    		ps = dbConnect.connectDB().prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            
            if(rs.next())
            {
            	Timestamp ts = rs.getTimestamp("u_bloqueio");
            	if(Objects.isNull(ts)) return false;
            	Date dateBlock = new Date(ts.getTime());
            	if(getDateDiff(dateBlock, TimeUnit.MINUTES) < 2) {
            		return true;
            	}
            }
    	}catch (SQLException  ex) {
    		System.out.println(ex.getMessage());
        }
    	
    	return false;
    }
	
	private static long getDateDiff(Date dateBlock, TimeUnit timeUnit) {
    	Date date1 = new Date(System.currentTimeMillis());
        long diffInMillies = date1.getTime() - dateBlock.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
	
	private void setUser(ResultSet rs) throws SQLException {
		User usr = new User();
		usr.setEmail(rs.getString("u_email"));
		usr.setName(rs.getString("u_nome"));
		usr.setPassword(rs.getString("u_senha"));
		usr.setgroup(rs.getInt("u_grupo"));
		usr.setSalt(rs.getString("u_salt"));
		usr.setBloquedAt(rs.getDate("u_bloqueio"));
		usr.setCertificate(rs.getString("u_certificado"));
		usr.setTotalAccesses(rs.getInt("u_acessos"));
		usr.setTotalReads(rs.getInt("u_leituras"));
		dbConnect.register(2003, usr.getEmail(), null);
		this.user = usr;
	}
	
	public static PublicKey getPublicKey(User user) throws Exception {

        String certificate = user.getCertificate();
        byte[] certificateBytes = certificate.getBytes();

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        InputStream certificateInputStream = new ByteArrayInputStream(certificateBytes);
        X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(certificateInputStream);  

        return x509Certificate.getPublicKey();
    }
	
	public static PrivateKey getPrivateKey(String password, Path path) throws Exception {

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(password.getBytes());

        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56, secureRandom);
        Key key = keyGenerator.generateKey();

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cipherPemBytes;
		try {
			cipherPemBytes = Files.readAllBytes(path);
		} catch (IOException e) {
            dbConnect.register(4004);
			throw new Exception("Path invalido.");
		}
        byte[] pemBytes = cipher.doFinal(cipherPemBytes);

        String pemString = new String(pemBytes);
        pemString = pemString.replace("-----BEGIN PRIVATE KEY-----\n","");
        pemString = pemString.replace("-----END PRIVATE KEY-----\n","");

        byte[] privateKeyBytes = Base64.getMimeDecoder().decode(pemString);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // TODO tirar isso depois
        privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }
	
	public static boolean isPrivateKeyValid(PrivateKey priKey, PublicKey pubKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        byte[] message = new byte[2048];
        (new SecureRandom()).nextBytes(message);

        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(priKey);
        signature.update(message);
        byte[] cipherMessage = signature.sign();

        signature.initVerify(pubKey);
        signature.update(message);

        if(signature.verify(cipherMessage)) {
        	privateKey = priKey;
        	publicKey = pubKey;
            return true;
        } else {
            return false;
        }
    }
	
}
