package service;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import model.User;
public class dbConnect {
	public static Connection connectDB(){
	     
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_secure_system", "root", "root");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return con;
    }
	
	private static String generateSalt() throws NoSuchAlgorithmException{
		
        String SALTCHARS = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        SecureRandom rand = new SecureRandom();
        while (salt.length() < 10) {
            int index = (int) (rand.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
	
	private static String toHex(byte[] data) {

		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(0x0100 + (data[i] & 0x00FF)).substring(1);
			buffer.append((hex.length() < 2 ? "0" : "") + hex);
		}

		return buffer.toString();
	}
	
	private static String saltedPassword(String pwd, String salt) {
		MessageDigest sha1 = null;
        try {
            sha1 = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Não encontrou algoritmo SHA1");
            return null;
        }
        sha1.update((pwd + salt).getBytes());
        return toHex(sha1.digest());
	}
	
	public static boolean checkUserPassword(String pwd, User user) {
		return user.getPassword().equals(saltedPassword(pwd, user.getSalt()));
	}
	
	public static void register(int msgId, String userName, String arq) {
		PreparedStatement ps;
		int rs = 0;
    	String query = "INSERT INTO `registro`(`r_idMensagem`, `r_email`, `r_nomeArq`) VALUES (?,?,?)";
    	
    	try {
    		ps = dbConnect.connectDB().prepareStatement(query);
    		ps.setInt(1, msgId);
    		ps.setString(2, userName);
            ps.setString(3, arq);
            rs = ps.executeUpdate();
            if(rs > 0)
            {
            	System.out.println("Mensagem "+msgId+ " cadastrada");
            }
            
    	}catch (SQLException  ex) {
    		System.out.println(ex.getMessage());
        }
	}
	
	public static void register(int msgId) {
		PreparedStatement ps;
    	int rs = 0;
    	String query = "INSERT INTO `registro`(`r_idMensagem`) VALUES (?)";
    	
    	try {
    		ps = dbConnect.connectDB().prepareStatement(query);
    		ps.setInt(1, msgId);
            rs = ps.executeUpdate();
            if(rs > 0)
            {
            	System.out.println("Mensagem "+msgId+ " cadastrada");
            }
            
    	}catch (SQLException  ex) {
    		System.out.println(ex.getMessage());
        }
	}

	public static boolean newUser(String name, String email, int group, String pwd) throws Exception, SQLException {
		boolean userCheck = AuthenticationService.getInstance().checkUserEmail(email);
		
		if(userCheck)
			throw new Exception("Já existe um usuário com este e-mail.");
		
		PreparedStatement ps;
    	int rs = 0;
    	String query = "INSERT INTO `usuarios`(`u_email`, `u_nome`, `u_senha`, `u_grupo`, `u_salt`, `u_certificado`,`u_acessos`, `u_leituras`) VALUES (?,?,?,?,?,?,?,?)";
    	
    	String salt = generateSalt();
    	String password = saltedPassword(pwd, salt);
    	
    	try {
    		ps = dbConnect.connectDB().prepareStatement(query);
    		ps.setString(1, email);
    		ps.setString(2, name);
    		ps.setString(3, password);
    		ps.setInt(4, group);
    		ps.setString(5, salt.toString());
    		ps.setString(6, "teset");
    		ps.setInt(7, 0);
    		ps.setInt(8, 0);
            rs = ps.executeUpdate();
            
            if(rs > 0)
            {
            	return true;
            }
            
    	}catch (SQLException  ex) {
    		System.out.println(ex.getMessage());
    		throw new SQLException(ex.getMessage());
        }
    	
		return false;
	}
	
	public static void updateUser(User user) {
		PreparedStatement ps;
    	String query = "UPDATE `usuarios` SET `u_email`=?,`u_nome`=?,`u_senha`=?,`u_grupo`=?,`u_salt`=?,`u_bloqueio`=?,`u_certificado`=?,`u_acessos`=?,`u_leituras`=? WHERE `u_email`=?";
    	
    	try {
    		ps = dbConnect.connectDB().prepareStatement(query);
    		ps.setString(1, user.getEmail());
    		ps.setString(2, user.getName());
    		ps.setString(3, user.getPassword());
    		ps.setInt(4, user.getgroup());
    		ps.setString(5, user.getSalt());
    		ps.setObject(6, user.getBloquedAt());
    		ps.setString(7, user.getCertificate());
    		ps.setInt(8, user.getTotalAccesses());
    		ps.setInt(9, user.getTotalReads());
    		ps.setString(10, user.getEmail());
            ps.executeUpdate();
            
    	}catch (SQLException  ex) {
    		System.out.println(ex.getMessage());
        }
	}
}
