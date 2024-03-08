package kr.smhrd.renewen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.UserVO;
import kr.smhrd.renewen.service.PlantService;
import kr.smhrd.renewen.service.PlantStatsService;

@Controller
public class AuthController {
	
	@Autowired
	PlantService plantService;
	
	@Autowired
	PlantStatsService plantStatsService;
	
	// 발전소 승인목록 페이지
	@GetMapping("/plantAuth")
	public String GrantPlantList(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		if(!"ROLE_ADMIN".equals(user.getAuthId())) {
			return "redirect:/";
		}
		
		List<PowerPlantVO> list = plantService.getNotGrantPlants();
		model.addAttribute("list", list);
		session.setAttribute("plantNo", list);

		return "views/plant/plant_auth";
	}
	
	@PostMapping("/plantAuth")
	public String GrantPlant(PowerPlantVO plant) {
		
		plantService.grantPlant(plant);
		return "redirect:/plantAuth";
	}
}
