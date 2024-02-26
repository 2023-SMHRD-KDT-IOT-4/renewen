package kr.smhrd.renewen.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.smhrd.renewen.model.UserVO;

@Mapper
public interface UserMapper {

	// 로그인 시 id, pw 일치한지
	public int loginCheck(UserVO user);
	public UserVO getUser(String userId);
	public int joinUser(UserVO user);
}
 