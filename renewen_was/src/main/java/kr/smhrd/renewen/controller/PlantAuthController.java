package kr.smhrd.renewen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.UserVO;
import kr.smhrd.renewen.service.PlantService;
import kr.smhrd.renewen.service.PlantStatsService;

@Controller
public class PlantAuthController {
	
	@Autowired
	PlantService plantService;
	
	@Autowired
	PlantStatsService plantStatsService;
	
	@GetMapping("/plantAuth")
	public String GrantList(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		String userId = user.getUserId();
		List<PowerPlantVO> list = plantService.getPlantsByUserId(userId);
		model.addAttribute("list", list);
		session.setAttribute("plantNo", list);
		System.out.println("세션에 저장된 값"+session.getAttribute("plantNo"));
		return "views/plant/plant_auth";
	}
}
