package com.xtone.shiro.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.xtone.shiro.db.JdbcControl;
import com.xtone.shiro.db.QueryCallBack;
import com.xtone.shiro.model.User;

public class ShiroUserDao {
	private final static Logger LOG = Logger.getLogger("ShiroUserDao.class");
	
	public Map<String, Object> getUserByName(String userName){
		String sql = "SELECT * FROM `users` WHERE `username`='"+userName+"' AND stute=1";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("list", new JdbcControl().query(sql, new QueryCallBack() {
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException {
				List<User> list = new ArrayList<User>();
				
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setPasserword(rs.getString("password"));
					user.setStute(rs.getInt("stute"));
					list.add(user);
				}
				
				return list;
			}
		}));
		
		return map;
		
	}
	

	@SuppressWarnings("unchecked")
	public Set<String> getRoles(String userName) {
		String sql = "SELECT * FROM `user_roles` WHERE `username` = '"+userName+"'";
		return (Set<String>) new JdbcControl().query(sql, new QueryCallBack() {
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException {
				Set<String> roles = new HashSet<>();
				while(rs.next()) {
					roles.add(rs.getString("role_name"));
				}
				return roles;
			}
		});
		
	}


	@SuppressWarnings("unchecked")
	public Set<String> getPermissions(String userName) {
		String sql = "SELECT * FROM `roles_permissions` AS p,`user_roles` AS r WHERE r.`role_name`=p.`role_name` AND r.`username`='"+userName+"'";
		return (Set<String>) new JdbcControl().query(sql, new QueryCallBack() {
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException {
				Set<String>permission = new HashSet<>();
				while (rs.next()) {
					permission.add(rs.getString("permission"));
				}
				return permission;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<User> loadUser(){
		String sql = "SELECT a.*,b.`role_name`,b.`zh_name` FROM `users` AS a LEFT JOIN `user_roles` AS b ON a.`username`= b.`username` GROUP BY a.`username`  ORDER BY a.id";
		
		return (List<User>) new JdbcControl().query(sql, new QueryCallBack() {
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException {
				List<User>list = new ArrayList<>();
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setPasserword(rs.getString("password"));
					user.setStute(rs.getInt("stute"));
					user.setZhName(rs.getString("zh_name"));
					list.add(user);
				}
				return list;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<User> loadRoleList() {
		String sql = "SELECT role_name,zh_name FROM `user_roles` GROUP BY role_name";
		return (List<User>) new JdbcControl().query(sql, new QueryCallBack() {
			
			@Override
			public Object onCallBack(ResultSet rs) throws SQLException {
				List<User> list = new ArrayList<>();
				while (rs.next()) {
					User user = new User();
					user.setRoleName(rs.getString("role_name"));
					user.setZhName(rs.getString("zh_name"));
					list.add(user);
				}
				return list;
			}
		});
	}
	
}
