package kr.smhrd.renewen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;
import kr.smhrd.renewen.model.CloudShotImgVO;
import kr.smhrd.renewen.model.PowerPlantVO;
import kr.smhrd.renewen.model.SensingDataVO;
import kr.smhrd.renewen.model.UserVO;
import kr.smhrd.renewen.service.PlantService;

@Controller
public class PlantController { 

	@Autowired
	PlantService plantService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/plant/register")
	public String plantRegister() {
		return "views/plant/plant_register";
	}
	
	@PostMapping("/plant/register")
	public String plantRegister(PowerPlantVO plant, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		String userId = user.getUserId();
		// dummy data
		plant.setUserId(userId);
		plant.setPlantAddr("test addr");
		plant.setBrNumber("test Br");
		plantService.registerPlant(plant);
		
		return "redirect:/plant/list";
	}
	
	@GetMapping("/plant/list")
	public String plantList(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		String userId = user.getUserId();
		List<PowerPlantVO> list = plantService.getPlantsByUserId(userId);
		model.addAttribute("list", list);
		
		return "views/plant/plant_list";
	}
	
	@GetMapping("/plant/cloudImgs")
	public String imgList(Model model, HttpSession session) {
		
		UserVO user = (UserVO) session.getAttribute("user");
		String userId = user.getUserId();
		List<PowerPlantVO> plantList = plantService.getPlantsByUserId(userId);
		
		if(plantList.isEmpty()) {
			return "views/plant/cloudImgs"; 
		}
		
		long plantNo = plantList.get(0).getPlantNo();
		List<CloudShotImgVO> list = plantService.getCloudImgsByPlantNo(plantNo);
		model.addAttribute("cloudImgs", list);
		
		return "views/plant/cloudImgs";
	}

}
