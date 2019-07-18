package com.dayup.seckil;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dayup.seckil.VO.userVO;
import com.dayup.seckil.model.User;
import com.dayup.seckil.redis.UserRedis;
import com.dayup.seckil.service.UserService;
import com.dayup.seckil.util.MD5util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRedis userRedis;
	
	@Test
	public void test() {
		User user = new User("alex","12345",8);
		Assert.assertNotNull(userService.regist(user));
	}
	
	@Test
	public void testGetUser() {
		Assert.assertNotNull(userService.getUser("ppoi"));
	}
	
	@Test
	public void testPassword() {
		userVO user = userService.getUser("wsdjie");
		String password = MD5util.inputToDb("123456", "");
		Assert.assertEquals(password, user.getPassword());
	}
	
	@Test
	public void testPutRedis() {
		User user = new User("alex2","123456");
		userRedis.put(user.getUsername(),user,-1);
	}
}
