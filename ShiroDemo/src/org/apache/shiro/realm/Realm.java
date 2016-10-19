package org.apache.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

public interface Realm {
	
	String getName(); // 返回一个唯一的Realm名字

	boolean supports(AuthenticationToken token); // 判断此Realm是否支持此Token

	AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException; // 根据Token获取认证信息

}
