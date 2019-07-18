package com.dayup.seckil.service;

import com.dayup.seckil.VO.userVO;
import com.dayup.seckil.model.User;

public interface UserService {

	public User regist(User user);

	public userVO getUser(String username);

	public void saveUserToRedisByToken(userVO dbUser, String token);

	public Object getUserFromRedisByToken(String token);
}
