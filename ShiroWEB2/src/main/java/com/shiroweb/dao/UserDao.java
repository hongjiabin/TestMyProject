package com.shiroweb.dao;

import java.util.Set;

import com.shiroweb.entity.User;

public interface UserDao {

	/**
	 * 通过用户名查询用户
	 * @param userName
	 * @return
	 */
	public User getByUserName(String userName);
	
	/**
	 * 通过用户名查询角色信息
	 * @param userName
	 * @return
	 */
	public Set<String> getRoles(String userName);
	
	/**
	 * 通过用户名查询权限信息
	 * @param userName
	 * @return
	 */
	public Set<String> getPermissions(String userName);
}
