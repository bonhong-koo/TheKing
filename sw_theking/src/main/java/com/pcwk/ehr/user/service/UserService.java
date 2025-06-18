package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.user.domain.UserDTO;

public interface UserService {

	// BASIC -> SILVER 로그인 횟수
	int MIN_LOGIN_COUNT_FOR_SILVER = 50;
	// SILVER -> GOLD 추천횟수
	int MIN_RECOMMEND_FOR_GOLD = 30;

	UserDTO doSelectOne(UserDTO param) throws SQLException;

	int doUpdate(UserDTO param);

	int doDelete(UserDTO param);

	List<UserDTO> doRetrieve(UserDTO param);

	int doSave(UserDTO param) throws SQLException;

	// 전체 회원을 대상으로 등록
	// ...
	void upgradeLevels() throws SQLException;

}