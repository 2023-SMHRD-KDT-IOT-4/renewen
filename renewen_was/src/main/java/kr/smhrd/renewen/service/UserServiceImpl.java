package kr.smhrd.renewen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.smhrd.renewen.mapper.UserMapper;
import kr.smhrd.renewen.model.UserAuthVO;
import kr.smhrd.renewen.model.UserVO;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserVO login(UserVO user) {
		
		int result = userMapper.loginCheck(user);
		
		if(result > 0) {
			UserVO loginUser = userMapper.getUser(user.getUserId());
			return loginUser;
		}
		
		return null;
	}

	@Override
	public int joinUser(UserVO user) {
		return userMapper.joinUser(user);
	}

	@Override
	public List<UserAuthVO> getValidAuthList() {
		return userMapper.getValidAuthList();
	}
	
	@Override
	public int updateUser(UserVO user) {
		return userMapper.updateUser(user);
	}

}
