package com.avecircle.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avecircle.bindings.LoginForm;
import com.avecircle.bindings.RegisterForm;
import com.avecircle.bindings.ResetPwdForm;
import com.avecircle.entity.City;
import com.avecircle.entity.Country;
import com.avecircle.entity.State;
import com.avecircle.entity.User;
import com.avecircle.props.AppProps;
import com.avecircle.repo.CityRepo;
import com.avecircle.repo.CountryRepo;
import com.avecircle.repo.StateRepo;
import com.avecircle.repo.UserRepo;
import com.avecircle.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private AppProps props;

	@Override
	public Map<Integer, String> getCountries() {

		Map<Integer, String> countries = new HashMap<>();
		List<Country> listcountries = countryRepo.findAll();

		listcountries.forEach(c -> {
			countries.put(c.getCountryId(), c.getCountryName());
		});
		return countries;
	}

	@Override
	public Map<Integer, String> getStates(Integer cid) {

		Map<Integer, String> states = new HashMap<>();
		List<State> liststates = stateRepo.findByCountryId(cid);

		liststates.forEach(s -> {
			states.put(s.getStateId(), s.getStateName());
		});

		return states;
	}

	@Override
	public Map<Integer, String> getCities(Integer sid) {

		Map<Integer, String> cities = new HashMap<>();
		List<City> listcities = cityRepo.findByStateId(sid);
		
		listcities.forEach(c->{
			cities.put(c.getCityId(), c.getCityName());
		});		
		return cities;
	}

	
	@Override
	public User getUsers(String email) {
		
		return userRepo.findByEmailId(email);
		
	}

	
	@Override
	public boolean saveUser(RegisterForm formObj) {
		
		formObj.setPwd(generatePwd());
		formObj.setPwdUpdated("NO");
		
		User user=new User();
		BeanUtils.copyProperties(formObj, user);
		
		userRepo.save(user);
		
		String sub=props.getMessages().get("emailSub");
		String body="<h1>Your Password: "+formObj.getPwd()+"</h1>";
		String to=formObj.getEmailId();
		return emailUtils.sendEmail(sub, body, to);
	}

	private String generatePwd() {
		
		String characters ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@";
		StringBuffer bufferObj = new StringBuffer(5);
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			int index = random.nextInt(characters.length() - 1);
			char randomString = characters.charAt(index);
			bufferObj.append(randomString);
		}
		return bufferObj.toString();
	}

	@Override
	public User loginCheck(LoginForm formObj) {
		User user = userRepo.findByEmailIdAndPwd(formObj.getEmailId(), formObj.getPwd());		
		return user;
	}


	@Override
	public boolean resetPwd(ResetPwdForm formObj) {
		
		Optional<User> findByuser = userRepo.findById(formObj.getUserId());
		if (findByuser.isPresent()) {
			User user = findByuser.get();
			user.setPwd(formObj.getNewPwd());
			user.setPwdUpdated("YES");
			userRepo.save(user);
			return true;
		}
		return false;
	}



}
