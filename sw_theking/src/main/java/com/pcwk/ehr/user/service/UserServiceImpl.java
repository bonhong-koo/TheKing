package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.UserDTO;

@Service
public class UserServiceImpl implements UserService {
	Logger log = LogManager.getLogger(getClass());

	@Qualifier("dummyMailService") //bean id로 특정 빈 주입
	@Autowired
	private MailSender mailSender;

	@Autowired
	private UserMapper mapper;
	
	
	public UserServiceImpl() {
	}

	
	@Override
	public List<UserDTO> doRetrieve(UserDTO param) {
		return mapper.doRetrieve(param);	
	}
	
	@Override
	public int doDelete(UserDTO param) {
		return mapper.doDelete(param);
	}
	
	@Override
	public int doUpdate(UserDTO param) {
		return mapper.doUpdate(param);
	}
	
	@Override
	public UserDTO doSelectOne(UserDTO param) throws SQLException {
		return mapper.doSelectOne(param);
	}
	
	@Override
	public int doSave(UserDTO param) throws SQLException {
		// 1. 정규표현식 검사
	    if (!isValidUserId(param.getUserId())) {
	        throw new IllegalArgumentException("아이디는 영문자, 숫자, _,- 포함 6~12자리여야 합니다.");
	    }
	    if (!isValidEmail(param.getEmail())) {
	        throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
	    }
	    if (!isValidMobile(param.getMobile())) {
	        throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)");
	    }
	    if (!isValidPassword(param.getPassword())) {
	        throw new IllegalArgumentException("비밀번호는 영문, 숫자, 특수문자를 포함한 8~20자여야 합니다.");
	    }

	    // 2. 아이디 중복 체크
	    UserDTO duplicateCheck = mapper.doSelectOne(param);
	    if (duplicateCheck != null) {
	        throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
	    }

	    // 3. 저장
	    return mapper.doSave(param);
	}
	
	//ADMIN인지 확인
	private boolean isAdmin(UserDTO user) {
	    return "ADMIN".equalsIgnoreCase(user.getRole());
	}
	
	private boolean isValidUserId(String userId) {
	    return userId != null && userId.matches("^[A-Za-z0-9_-]{6,12}$");
	}

	private boolean isValidEmail(String email) {
	    return email != null && email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
	}

	private boolean isValidMobile(String mobile) {
	    return mobile != null && mobile.matches("^01[016789]-\\d{3,4}-\\d{4}$");
	}

	private boolean isValidPassword(String password) {
	    return password != null &&
	           password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,20}$");
	}
	
}
// class 끝
