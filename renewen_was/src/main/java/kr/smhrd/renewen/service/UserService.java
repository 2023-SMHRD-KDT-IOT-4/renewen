package kr.smhrd.renewen.service;

import java.util.List;

import kr.smhrd.renewen.model.UserAuthVO;
import kr.smhrd.renewen.model.UserVO;


public interface UserService {

	public UserVO login(UserVO user);
	public int joinUser(UserVO user);
	public List<UserAuthVO> getValidAuthList();
	public int updateUser(UserVO user);
	
}
