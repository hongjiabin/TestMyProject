package com.xtone.shiro.model;

public class User {
	private int id;
	private String username;
	private String passerword;
	private int stute;
	private String roleName;//角色名
	private String zhName;//中文角色名
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasserword() {
		return passerword;
	}
	public void setPasserword(String passerword) {
		this.passerword = passerword;
	}
	public int getStute() {
		return stute;
	}
	public void setStute(int stute) {
		this.stute = stute;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getZhName() {
		return zhName;
	}
	public void setZhName(String zhName) {
		this.zhName = zhName;
	}
	
	
}
