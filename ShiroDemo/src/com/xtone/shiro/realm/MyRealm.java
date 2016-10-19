package com.xtone.shiro.realm;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.xtone.shiro.dao.ShiroUserDao;
import com.xtone.shiro.model.User;

public class MyRealm extends AuthorizingRealm{
	
	public ShiroUserDao dao = new ShiroUserDao();

	/**
	 * 为当前登录用户授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorization = new SimpleAuthorizationInfo();
		authorization.setRoles(dao.getRoles(userName));
		authorization.setStringPermissions(dao.getPermissions(userName));
		return authorization;
	}

	/**
	 * 验证当前登录的用户
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String)token.getPrincipal();
		Map<String, Object>map = dao.getUserByName(username);
		List<User> list = (List<User>) map.get("list");
		for (User user : list) {
			if(user != null){
				AuthenticationInfo authentication = new SimpleAuthenticationInfo(user.getUsername(),user.getPasserword(),"user");
				return authentication;
			}
		}
		return null;
	}

}
