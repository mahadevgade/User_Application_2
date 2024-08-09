package com.avecircle.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avecircle.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	public User findByEmailId(String email);
	
	public User findByEmailIdAndPwd(String email, String pwd);
}
