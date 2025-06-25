/**
 * Package Name : com.pcwk.ehr.user <br/>
 * 파일명: UserServiceTest.java <br/> 
 */
package com.pcwk.ehr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.pattern.EqualsIgnoreCaseReplacementConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.UserDTO;
import com.pcwk.ehr.user.service.TestUserService;
import com.pcwk.ehr.user.service.UserService;

/**
 * @author user
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" 
		, "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class UserServiceTest {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;

	@Autowired
	UserService userService;

	@Autowired
	UserDao userDao;

	List<UserDTO> users;

	@Autowired
	DataSource dataSource;

	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Qualifier("dummyMailService") //bean id로 특정 빈 주입
	@Autowired
	MailSender mailSender;
	
//	@Autowired
//	TestUserService testUserService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");

		users = Arrays.asList(
				
				new UserDTO("pcwk01", "pcwk01234!", "이상무", "이상무01", "pcwk01@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user",null, "사용안함", "사용안함"),
				new UserDTO("pcwk01", "pcwk01234!", "이상무", "이상무01", "pcwk02@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user",null, "사용안함", "사용안함"),
				new UserDTO("pcwk01", "pcwk01234!", "이상무", "이상무01", "pcwk03@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user",null, "사용안함", "사용안함"),
				new UserDTO("pcwk01", "pcwk01234!", "이상무", "이상무01", "pcwk04@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user",null, "사용안함", "사용안함"),
				new UserDTO("admin", "admin123!", "관리자", "관리자", "admin01@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user",null, "사용안함", "사용안함"));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

	@Disabled
	@Test
	void upgradeAllOrNothing() throws SQLException {
		log.debug("┌─────────────────────────────────┐");
		log.debug("│ upgradeAllOrNothing()           │");
		log.debug("└─────────────────────────────────┘");
		// 매번 동일한 결과가 도출 되도록 작성
		// 1.전체삭제
		// 2.5명 사용자 모두 입력
		// 3.등업
		// 3.1 예외
		
		try {
			// 1.
			userDao.deleteAll();
			assertEquals(0, userDao.getCount());

			// 2.
			for (UserDTO dto : users) {
				userDao.doSave(dto);
			}
			assertEquals(5, userDao.getCount());

			// 3.
//			testUserService.upgradeLevels();

		} catch (Exception e) {
			log.debug("┌─────────────────────────────────┐");
			log.debug("│ Exception()                     │" + e.getMessage());
			log.debug("└─────────────────────────────────┘");
		}
		
//		checkLevel(users.get(1), false);
	}

	@Disabled
	@Test
	public void doSave() throws SQLException {
		// 매번 동일한 결과가 도출 되도록 작성
		// 1.전체삭제
		// 2.등급있는 사용자 등록
		// 2.등급없는 사용자 등록
		// 3.조회
		// 4.비교

		log.debug("┌─────────────────────────┐");
		log.debug("│ *doSave()*              │");
		log.debug("└─────────────────────────┘");

		// 1.
		userDao.deleteAll();
		assertEquals(0, userDao.getCount());

		// 2.
		UserDTO userWithrole = users.get(4);
		userService.doSave(userWithrole);
		assertEquals(1, userDao.getCount());

		UserDTO userWithOutrole = users.get(0);
		userWithOutrole.setRole(null);
		userService.doSave(userWithOutrole);
		assertEquals(2, userDao.getCount());

		// 3.
		UserDTO roleAdmin = userDao.doSelectOne(userWithrole);
		UserDTO roleUser = userDao.doSelectOne(userWithOutrole);
		assertEquals(roleAdmin.getRole(), "admin");
		assertEquals(roleUser.getRole(), "User");

	}

	@Disabled
	@Test
	public void upgradeLevels() throws SQLException {
		// 매번 동일한 결과가 도출 되도록 작성
		// 1.전체삭제
		// 2.5명 사용자 모두 입력
		// 3.등업
		// 4.데이터로 비교
		log.debug("┌─────────────────────────┐");
		log.debug("│ *upgradeLevels()*       │");
		log.debug("└─────────────────────────┘");

		// 1.
		userDao.deleteAll();
		assertEquals(0, userDao.getCount());

		// 2.

		for (UserDTO dto : users) {
			userDao.doSave(dto);
		}

		assertEquals(5, userDao.getCount());

		// 3.
		//userService.upgradeLevels();

		userService.doSelectOne(users.get(0));
		// 4.
//		checkLevel(users.get(0), false);
//		checkLevel(users.get(1), true);
//		checkLevel(users.get(2), false);
//		checkLevel(users.get(3), true);
//		checkLevel(users.get(4), false);

//		checkLevel(UserDTO user, boolean upgraded)
	}

//	private void checkLevel(UserDTO user, boolean upgraded) throws SQLException {
//		UserDTO upgradeUser = userDao.doSelectOne(user);
//
//		// 등업
//		if (upgraded == true) {
//			assertEquals(upgradeUser.getGrade(), user.getGrade().getNextLevel());
//		} else {// No 등업
//			assertEquals(upgradeUser.getGrade(), user.getGrade());
//		}
//
//	}

	//@Disabled
	@Test
	void beans() {
		assertNotNull(userDao);
		assertNotNull(context);
		assertNotNull(userService);
		assertNotNull(dataSource);
		assertNotNull(transactionManager);
		assertNotNull(mailSender);
		
		log.debug("context:" + context);
		log.debug("userService:" + userService);
		log.debug("userDao:" + userDao);
		log.debug("dataSource:" + dataSource);
		log.debug("transactionManager:" + transactionManager);
		log.debug("mailSender" + mailSender);
	}

}
