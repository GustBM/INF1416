package model;

import java.util.Date;

public class User {
	private String name;
	private String email;
	private int group;
	private String salt;
	private String password;
	private Date bloquedAt;
	private String certificate;
	private int totalAccesses;
	private int totalReads;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String username) {
		this.email = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getBloquedAt() {
		return bloquedAt;
	}
	
	public void setBloquedAt(Date bloquedAt) {
		this.bloquedAt = bloquedAt;
	}
	
	public String getCertificate() {
		return certificate;
	}
	
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getgroup() {
		return group;
	}
	
	public void setgroup(int group) {
		this.group = group;
	}
	
	public int getTotalAccesses() {
		return totalAccesses;
	}
	
	public void setTotalAccesses(int totalAccesses) {
		this.totalAccesses = totalAccesses;
	}
	
	public void addTotalAccesses() {
		this.totalAccesses++;
	}
	
	public int getTotalReads() {
		return totalReads;
	}
	
	public void setTotalReads(int totalReads) {
		this.totalReads = totalReads;
	}
}
