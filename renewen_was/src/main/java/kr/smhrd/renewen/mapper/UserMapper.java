package kr.smhrd.renewen.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.smhrd.renewen.model.UserAuthVO;
import kr.smhrd.renewen.model.UserLogVO;
import kr.smhrd.renewen.model.UserVO;

@Mapper
public interface UserMapper {

	// 로그인 시 id, pw 일치한지
	public int loginCheck(UserVO user);
	public UserVO getUser(String userId);
	public int joinUser(UserVO user);
	public List<UserAuthVO> getValidAuthList();
	public int updateUser(UserVO user);
	public int insertLog(UserLogVO log);
}
 