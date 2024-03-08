package kr.smhrd.renewen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.UserAuthVO;
import kr.smhrd.renewen.model.UserVO;
import kr.smhrd.renewen.service.PlantService;
import kr.smhrd.renewen.service.UserService;

@Controller
public class UserController { 

	@Autowired
	UserService userService;
	
	@Autowired
	PlantService plantService;
	
	@GetMapping("/user/login")
	public String login() {
		return "views/user/login";
	}
	
	/*
	@PostMapping("/user/login")
	public String login(UserVO user, HttpSession session,Model model) {
		
		System.out.println(user);
		UserVO loginUser = userService.login(user);
		System.out.println("logined!! " + loginUser);
		String userId = loginUser.getUserId();
		if(loginUser != null) {
			session.setAttribute("user", loginUser);
			List<PowerPlantVO> plantList = plantService.getPlantsByUserId(userId);
			System.out.println("로그인후 발전소 정보"+plantList);
			session.setAttribute("plantList", plantList);
			return "redirect:/";
		} else {
			return "redirect:/user/login";
		}
	}
	*/
	@PostMapping("/user/login")
	public String login(UserVO user, HttpSession session, Model model) {
	    System.out.println(user);
	    UserVO loginUser = userService.login(user);
	    System.out.println("logined!! " + loginUser);
	    if (loginUser != null) {
	        String userId = loginUser.getUserId();
	        session.setAttribute("user", loginUser);
	        List<PowerPlantVO> plantList = plantService.getPlantsByUserId(userId);
	        System.out.println("로그인후 발전소 정보" + plantList);
	        session.setAttribute("plantList", plantList);
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
	
	/*
	 * @PostMapping("/user/join") public String join(UserVO user) {
	 * 
	 * userService.joinUser(user);
	 * 
	 * return "redirect:/"; }
	 */
	
	@PostMapping("/user/join")
	public String join(UserVO user) {
	    try {
	        int result = userService.joinUser(user);
	        if (result < 1) {
	            return "views/user/join";
	        } else {
	            return "redirect:/";
	        }
	    } catch (DataIntegrityViolationException ex) {
	        return "views/user/join";
	    } 
	}
	
	// 회원정보 수정 페이지
	@GetMapping("/user/update")
	public String userUpdate(HttpSession session) {
		return "views/user/user_update";
	}
	
	@PostMapping("user/update")
	public String updateUser(UserVO user,Model model) {
		System.out.println(user);
		int res = userService.updateUser(user);
		if(res>0) {
			//model.addAttribute("updateSuccessMsg","회원정보 수정 성공!");
			return "redirect:/";
		}else {
			//model.addAttribute("updateFailMsg","회원정보 수정 실패!");
			return "redirect:/user/update";
		}
	}
	
	// 사용자 로그 확인 페이지
	/*
	 * @GetMapping("/user/log") public String userLogPage(HttpSession session) {
	 * 
	 * System.out.println("userLogPage");
	 * 
	 * return "views/user/user_log"; }
	 */
}

