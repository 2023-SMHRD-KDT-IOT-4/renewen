package kr.smhrd.renewen.service;

import kr.smhrd.renewen.model.UserVO;


public interface UserService {

	public UserVO login(UserVO user);
	public int joinUser(UserVO user);
	
}
