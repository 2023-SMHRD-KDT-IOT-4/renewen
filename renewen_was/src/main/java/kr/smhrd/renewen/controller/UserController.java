package kr.smhrd.renewen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import kr.smhrd.renewen.model.UserAuthVO;
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
	public String join(Model model) {
		List<UserAuthVO> authList = userService.getValidAuthList();
		model.addAttribute("authList", authList);
		return "views/user/join";
	}
	
	@PostMapping("/user/join")
	public String join(UserVO user) {
		
		userService.joinUser(user);
		
		return "redirect:/";
	}
}
