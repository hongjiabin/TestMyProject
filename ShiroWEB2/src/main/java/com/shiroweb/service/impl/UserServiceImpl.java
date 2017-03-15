package com.shiroweb.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shiroweb.dao.UserDao;
import com.shiroweb.entity.User;
import com.shiroweb.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	
	public User getByUserName(String userName) {
		return userDao.getByUserName(userName);
	}

	public Set<String> getRoles(String userName) {
		return userDao.getRoles(userName);
	}

	public Set<String> getPermissions(String userName) {
		return userDao.getPermissions(userName);
	}

}
