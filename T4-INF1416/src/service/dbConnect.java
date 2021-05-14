package service;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;

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
	
	public static String generateSalt() {
		Random RANDOM = new SecureRandom();
        byte[] salt = new byte[10];
        RANDOM.nextBytes(salt);
        System.out.println(Base64.getEncoder().encodeToString(salt));
        return Base64.getEncoder().encodeToString(salt);
    }
	
	public static void register(int msgId, User user, String arq) {
		PreparedStatement ps;
    	ResultSet rs;
    	String query = "INSERT INTO `registro`(`r_idMensagem`, `r_email`, `r_nomeArq`) VALUES (?,?,?)";
    	
    	try {
    		ps = dbConnect.connectDB().prepareStatement(query);
    		ps.setInt(1, msgId);
    		ps.setString(2, user.getEmail());
            ps.setString(3, arq);
            rs = ps.executeQuery();
            if(rs.next())
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
	
	/*public static User getUser(String email) {
		String query = String.format("SELECT * FROM usuarios WHERE u_email = %d", email);
		PreparedStatement ps;
    	ResultSet rs;
    	User user = null;
		try {
    		ps = dbConnect.connectDB().prepareStatement(query);
            rs = ps.executeQuery();
            
            if(rs.next())
                return user;
            
    	}catch (SQLException  ex) {
    		System.out.println(ex.getMessage());
        }
		return null;
		
	}*/

	/*public boolean newUser(String name, String email, int group, String salt, String pwd, String certDig) throws Exception {
		boolean userCheck = AuthenticationService.checkUserEmail(email);
		
		if(userCheck)
			throw new Exception("Já existe um usuário com este e-mail.");
		
		String query = String.format("INSERT INTO register (messageId, email, filename) VALUES ('%d', '%s', '%s')", name, email, group, salt, pwd, certDig);
		PreparedStatement ps;
    	ResultSet rs;
    	
		try {
    		ps = dbConnect.connectDB().prepareStatement(query);
            rs = ps.executeQuery();
            
            if(rs.next())
                return true;
            
    	}catch (SQLException  ex) {
    		System.out.println(ex.getMessage());
        }
		return false;
	}*/
}
