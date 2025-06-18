package com.pcwk.ehr.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.user.domain.UserDTO;

public interface UserDao {

	// 메서드가 없기에 수정할게 없음.
	int getCount() throws SQLException;

	int saveAll();

	List<UserDTO> doRetrieve(UserDTO param);

	int doDelete(UserDTO param);

	int doUpdate(UserDTO param);

	List<UserDTO> getAll();

	// 메서드가 없기에 수정할게 없음.
	void deleteAll() throws SQLException;

	UserDTO doSelectOne(UserDTO dto01) throws SQLException;

	/**
	 * 단건등록
	 * 
	 * @param param
	 * @return 1(성공)/0(실패)
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	int doSave(UserDTO param) throws SQLException;

	/**
	 * @param users
	 * @return
	 */
	UserDTO doSelectOne(List<UserDTO> users);

}