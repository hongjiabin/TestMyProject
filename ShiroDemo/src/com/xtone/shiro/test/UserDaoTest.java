package com.xtone.shiro.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.xtone.shiro.dao.ShiroUserDao;
import com.xtone.shiro.model.User;

public class UserDaoTest {
	@Test
	public void loadUserByUsername(){
		ShiroUserDao dao = new ShiroUserDao();
		Map<String, Object>map = dao.getUserByName("zhang");
		List<User> list = (List<User>) map.get("list");
		for (User user : list) {
			System.out.println(user.getId());
			System.out.println(user.getUsername());
			System.out.println(user.getPasserword());
			System.out.println(user.getStute());
		}
	}
	
	@Test
	public void loadRoles(){
		ShiroUserDao dao = new ShiroUserDao();
		Set<String>role = dao.getRoles("zhang");
		for (String string : role) {
			System.out.println(string);
		}
	}
	
	@Test
	public void loadPermission(){
		ShiroUserDao dao = new ShiroUserDao();
		Set<String>permission = dao.getPermissions("zhang");
		for (String string : permission) {
			System.out.println(string);
		}
	}
	
	@Test
	public void loadRoleList(){
		ShiroUserDao dao = new ShiroUserDao();
		List<User>list = dao.loadRoleList();
		for (User user : list) {
			System.out.println("RoleName:"+user.getRoleName());
			System.out.println("ZhName"+user.getZhName());
		}
	}
}
