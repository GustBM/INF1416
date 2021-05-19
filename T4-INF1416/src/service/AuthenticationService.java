package service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import model.User;

public class AuthenticationService {
	
	private static AuthenticationService authService = new AuthenticationService();
	
	private User user;
	
	public static AuthenticationService getInstance() {
		return authService;
	}
	
	public User getUser() {
        return this.user;
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
            	Timestamp ts= rs.getTimestamp("u_bloqueio");
            	Date dateBlock = new Date(ts.getTime());
            	if(Objects.isNull(dateBlock)) return false;
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
		dbConnect.register(2003, usr.getName(), null);
		this.user = usr;
	}
	
}
