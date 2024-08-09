package com.avecircle.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avecircle.bindings.LoginForm;
import com.avecircle.bindings.RegisterForm;
import com.avecircle.bindings.ResetPwdForm;
import com.avecircle.entity.User;
import com.avecircle.props.AppProps;
import com.avecircle.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppProps props;
	
	@GetMapping("/")
	public String index(Model model)
	{
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String loginCheck(@ModelAttribute("loginForm") LoginForm formObj,Model model)
	{
		User user = userService.loginCheck(formObj);
		if (user==null) {
			model.addAttribute("errmsg", props.getMessages().get("invalidCrendentials"));
			return "login";			
		}
		
		if (user.getPwdUpdated().equals("NO")) {
			
			ResetPwdForm form=new ResetPwdForm();
			form.setUserId(user.getUserId());
			
			model.addAttribute("resetForm", form);
			return "resetPwd"; 
		}
		
		return "redirect:dashboard";
	}
	
	@PostMapping("/reset-pwd")
	public String updatePwd(@ModelAttribute("resetForm") ResetPwdForm formObj,Model model)
	{
		if (!formObj.getNewPwd().equals(formObj.getConfirmPwd())) {
			model.addAttribute("errmsg", props.getMessages().get("pwdnotmatched"));
			return "resetPwd";
		}
		
		boolean status = userService.resetPwd(formObj);
		if (status) {
			return "redirect:dashboard";
		}
		model.addAttribute("errmsg",props.getMessages().get("pwdnotupadted"));
		return "resetPwd";
	}
	
	@GetMapping("/logout")
	public String logout(Model model)
	{
		return "redirect:/";
	}
	
	
	@GetMapping("/register")
	public String register(Model model)
	{
		model.addAttribute("registerForm", new RegisterForm());
		
		Map<Integer,String> countries = userService.getCountries();
		model.addAttribute("countries", countries);
		
		return "register";
	}
	
	@GetMapping("/getStates")
	@ResponseBody
	public Map<Integer, String> getStates(@RequestParam("countryId") Integer countryId)
	{
		return userService.getStates(countryId);
		
	}
	
	@GetMapping("/getCities")
	@ResponseBody
	public Map<Integer, String> getCities(@RequestParam("stateId") Integer stateId)
	{
		return userService.getCities(stateId);
		
	}
 
	
	@PostMapping("/register")
	public String userRegister(@ModelAttribute RegisterForm formObj,Model model)
	{
		boolean status = userService.saveUser(formObj);
		
		if (status) {
			model.addAttribute("smsg", props.getMessages().get("recordsaved"));
		} else {
			model.addAttribute("emsg", props.getMessages().get("recordnotsaved"));
		}
		
		Map<Integer,String> countries = userService.getCountries();
		model.addAttribute("countries", countries);
		
		return "register";
	}
	

}
