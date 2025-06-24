package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.UserDTO;

@Service
public class UserServiceImpl implements UserService {
	Logger log = LogManager.getLogger(getClass());

	@Qualifier("dummyMailService") //bean id로 특정 빈 주입
	@Autowired
	private MailSender mailSender;

	@Autowired
	private UserDao userDao;
	
	
	public UserServiceImpl() {
	}

	
	@Override
	public List<UserDTO> doRetrieve(UserDTO param) {
		return userDao.doRetrieve(param);	
	}
	
	@Override
	public int doDelete(UserDTO param) {
		return userDao.doDelete(param);
	}
	
	@Override
	public int doUpdate(UserDTO param) {
		return userDao.doUpdate(param);
	}
	
	@Override
	public UserDTO doSelectOne(UserDTO param) throws SQLException {
		return userDao.doSelectOne(param);
	}
	
	@Override
	public int doSave(UserDTO param) throws SQLException {
		return userDao.doSave(param);
	}
	
	//ADMIN인지 확인
	private boolean isAdmin(UserDTO user) {
	    return "ADMIN".equalsIgnoreCase(user.getRole());
	}

}
// class 끝
