package com.avecircle.service;

import java.util.Map;

import com.avecircle.bindings.LoginForm;
import com.avecircle.bindings.RegisterForm;
import com.avecircle.bindings.ResetPwdForm;
import com.avecircle.entity.User;

public interface UserService {
	
	public Map<Integer, String> getCountries();
	public Map<Integer, String> getStates(Integer cid);
	public Map<Integer, String> getCities(Integer sid);
	
	public boolean saveUser(RegisterForm formObj);
	public User loginCheck(LoginForm formObj);
	public User getUsers(String email);
	public boolean resetPwd(ResetPwdForm formObj);
	
	
	//private String generatePwd();
	
	


	
	

}
