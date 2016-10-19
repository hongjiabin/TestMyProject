package com.github.zhangkaitao.shiro.chapter3;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class RoleTest {
	@Test
	public void testHasRole() {
		login("F:/WorkSpace/GIT_SPACE/xtone-public-base/ShiroDemo/src/shiro-role.ini", "zhang", "123");

		Subject subject = SecurityUtils.getSubject();
		// 判断拥有角色：role1
		Assert.assertTrue(subject.hasRole("role1"));
		// 判断拥有角色：role1 and role2
		Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1", "role2")));
		// 判断拥有角色：role1 and role2 and !role3
		boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));
		Assert.assertEquals(true, result[0]);
		Assert.assertEquals(true, result[1]);
		Assert.assertEquals(false, result[2]);
	}

	@Test(expected = UnauthenticatedException.class)
	public void testCheckPermission() {
		login("F:/WorkSpace/GIT_SPACE/xtone-public-base/ShiroDemo/src/shiro-role.ini", "zhang", "123");

		Subject subject = SecurityUtils.getSubject();
		// 断言拥有权限：user:create
		subject.checkPermission("user:create");
		// 断言拥有权限：user:delete and user:update
		subject.checkPermissions("user:delete", "user:update");
		// 断言拥有权限：user:view 失败抛出异常
		subject.checkPermissions("user:view");
	}

	private void login(String configFile, String name, String pwd) {
		// 1、获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(configFile);

		// 2、得到SecurityManager实例 并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		// 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);

		subject.login(token); 
	}
}
