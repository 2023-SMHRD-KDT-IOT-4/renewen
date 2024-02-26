package kr.smhrd.renewen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import kr.smhrd.renewen.model.UserVO;
import kr.smhrd.renewen.service.UserService;

@Controller
public class UserController { 

	@Autowired
	UserService userService;
	
	@GetMapping("/user/login")
	public String login() {
		return "views/user/login";
	}
	
	@PostMapping("/user/login")
	public String login(UserVO user, HttpSession session) {
		
		System.out.println(user);
		UserVO loginUser = userService.login(user);
		System.out.println("logined!! " + loginUser);
		if(loginUser != null) {
			session.setAttribute("user", loginUser);
			return "redirect:/";
		} else {
			return "redirect:/user/login";
		}
	}
	
	@GetMapping("/user/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
	
	@GetMapping("/user/join")
	public String join() {
		return "views/user/join";
	}
	
	@PostMapping("/user/join")
	public String join(UserVO user) {
		
		System.out.println(user);
		// 임의 데이터 세팅
		String userId = user.getUserId();
		user.setAuthId("ROLE_PLANT_ADMIN");
		user.setUserEmail(userId + "@renewen.com");
		user.setUserName(userId + "name");
		user.setUserTel("010-1111-2222");
		
		userService.joinUser(user);
		
		return "redirect:/";
	}
}
