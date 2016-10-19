package com.github.zhangkaitao.shiro.chapter2;

import static org.junit.Assert.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class LoginLogoutTest {

	/**
	 * 通过加载shiro.ini配置文件的方式读取相应的账号信息，加以验证
	 */
	@Test
	public void testHelloworld() {
		// 1、获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"F:/WorkSpace/Eclipse/ShiroDemo/src/shiro.ini");
		// 2、得到SecurityManager实例 并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

		try {
			// 4、登录，即身份验证
			subject.login(token);
		} catch (AuthenticationException e) {
			// 5、身份验证失败
			System.out.println("身份验证失败");
		}

		Assert.assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录

		// 6、退出
		subject.logout();
	}

	/**
	 * 通过加载shiro-realm.ini配置文件的方式，获取Realm（域），
	 * 从Realm中获取相应的用户信息加以验证，此处是展示的单个Realm
	 * 的验证形式
	 */
	@Test
	public void testRealm1() {
		// 1、获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"F:/WorkSpace/GIT_SPACE/xtone-public-base/ShiroDemo/src/shiro-realm.ini");
		// 2、得到SecurityManager实例 并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

		try {
			// 4、登录，即身份验证
			subject.login(token);
		} catch (AuthenticationException e) {
			// 5、身份验证失败
			System.out.println("身份验证失败");
		}

		Assert.assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录

		// 6、退出
		subject.logout();
	}

	/**
	 * 通过加载shiro-jdbc-realm.ini配置文件的形式，加载相关的JDBC参数及连接池参数
	 * 从中获取用户信息加以验证
	 */
	@Test
	public void testJDBCRealm() {
		// 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"F:/WorkSpace/GIT_SPACE/xtone-public-base/ShiroDemo/src/shiro-jdbc-realm.ini");

		// 2、得到SecurityManager实例 并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		// 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

		try {
			// 4、登录，即身份验证
			subject.login(token);
			if(subject.isAuthenticated()){
				System.out.println("登录成功");
			}
		} catch (AuthenticationException e) {
			// 5、身份验证失败
			e.printStackTrace();
		}

		//Assert.assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录

		// 6、退出
		subject.logout();
	}

	@After
	public void tearDown() throws Exception {
		ThreadContext.unbindSubject();// 退出时请解除绑定Subject到线程 否则对下次测试造成影响
	}

}
