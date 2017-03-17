package com.shiroweb.entity;

import java.io.Serializable;

public class UrlFilter implements Serializable {
	
	private static final long serialVersionUID = 3037676307998700301L;
	
	private Long id;  
    private String name; //url����/����  
    private String url; //��ַ  
    private String roles; //����Ҫ�Ľ�ɫ
    private String permissions; //����Ҫ��Ȩ��
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getPermissions() {
		return permissions;
	}
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
    
    
}
