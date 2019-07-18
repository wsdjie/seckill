package com.dayup.seckil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dayup.seckil.model.User;

@Repository //↓第一个对应的是实体映射，第二个对应的是主键类型
public interface UserRpository extends JpaRepository<User, String>{
	public User findByUsername(String password);
}
