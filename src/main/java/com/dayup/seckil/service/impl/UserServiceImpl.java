package com.dayup.seckil.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayup.seckil.VO.userVO;
import com.dayup.seckil.model.User;
import com.dayup.seckil.redis.UserRedis;
import com.dayup.seckil.repository.UserRpository;
import com.dayup.seckil.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	public UserRpository userRpository;
	
	@Autowired
	public UserRedis userRedis;
	
	@Override
	public User regist(User user) {
		return userRpository.saveAndFlush(user);
	}

	@Override
	public userVO getUser(String username) {
		userVO userVO = new userVO();
		User user = (User) userRedis.get("username");
		if(user == null){
			user = userRpository.findByUsername(username);
			if(user != null){
				userRedis.put(user.getUsername(), user, -1);
			}else{
				return null;
			}
		}
		BeanUtils.copyProperties(user, userVO);
		
		return userVO;
	}

	@Override
	public void saveUserToRedisByToken(userVO dbUser, String token) {
		User user = new User(); //构建一个User对象
		BeanUtils.copyProperties(dbUser, user);//赋值
		userRedis.put(token, user, 3600);//将标识码，用户数据，到期时间上传到redis
	}

	@Override
	public Object getUserFromRedisByToken(String token) {
		return userRedis.get(token);
	}

}
