package com.pcwk.ehr;
/**
 * Package Name : com.pcwk.ehr.user <br/>
 * 파일명: UserServiceTest.java <br/> 
 */


import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_LOGIN_COUNT_FOR_SILVER;
import static com.pcwk.ehr.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.UserDTO;
import com.pcwk.ehr.user.service.TestUserService;
import com.pcwk.ehr.user.service.UserService;
import com.pcwk.ehr.user.service.UserServiceImpl;

/**
 * @author user
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml"
		,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"	})
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
	
	@Qualifier("dummyMailService")
	@Autowired
	MailSender mailSender;
	
	@Autowired
	TestUserService testUserService;
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");

		users = Arrays.asList(
				// BASIC 그대로
				// BASIC -> SILVER
				// SILVER 그대로
				// SILVER -> GOLD
				// GOLD 그대로
				new UserDTO("pcwk01", "이상무01", "4321a", "사용안함", MIN_LOGIN_COUNT_FOR_SILVER - 1, 0, Level.BASIC,
						"etbonhong9@gmail.com"),
				new UserDTO("pcwk02", "이상무02", "4321a", "BASIC -> SILVER", MIN_LOGIN_COUNT_FOR_SILVER, 0, Level.BASIC,
						"etbonhong9@gmail.com"),
				new UserDTO("pcwk03", "이상무03", "4321a", "사용안함", MIN_LOGIN_COUNT_FOR_SILVER + 10,
						MIN_RECOMMEND_FOR_GOLD - 1, Level.SILVER, "etbonhong9@gmail.com"),
				new UserDTO("pcwk04", "이상무04", "4321a", "SILVER -> GOLD", MIN_LOGIN_COUNT_FOR_SILVER + 11,
						MIN_RECOMMEND_FOR_GOLD, Level.SILVER, "etbonhong9@gmail.com"),
				new UserDTO("pcwk05", "이상무05", "4321a", "사용안함", MIN_LOGIN_COUNT_FOR_SILVER + 50,
						MIN_RECOMMEND_FOR_GOLD + 7, Level.GOLD, "etbonhong9@gmail.com")

		);
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

	//@Disabled
	@Test
	void upgradeAllOrNothing() throws SQLException {
		log.debug("┌─────────────────────────┐");
		log.debug("│ *upgradeAllOrNothing()* │");
		log.debug("└─────────────────────────┘");
		// 매번 동일한 결과가 도출 되도록 지정
		// 1.전체삭제
		// 2. 5명 사용자 모두 입력
		// 3. 등업
		// 4. 예외

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
			testUserService.upgradeLevels();

		} catch (Exception e) {
			log.debug("┌─────────────────────────┐");
			log.debug("│ *Exception()*           │" + e.getMessage());
			log.debug("└─────────────────────────┘");
		}
		// 트랜잭션 적용이 않되고 있음!
		checkLevel(users.get(1), false);
	}

	@Disabled
	@Test
	public void doSave() throws SQLException {
		// 매번 동일한 결과가 도출 되도록 지정
		// 1.전체삭제
		// 2. 등급있는 사용자 등록
		// 3. 등급없는 사용자 등록
		// 4. 조회
		// 5. 비교
		log.debug("┌─────────────────────────┐");
		log.debug("│ *doSave()*              │");
		log.debug("└─────────────────────────┘");

		// 1.
		userDao.deleteAll();
		assertEquals(0, userDao.getCount());

		// 2.

		UserDTO userWithLevel = users.get(4);
		userService.doSave(userWithLevel);
		assertEquals(1, userDao.getCount());

		UserDTO userWithOutLevel = users.get(0);
		userWithOutLevel.setGrade(null);
		userService.doSave(userWithOutLevel);

		// 3.
		UserDTO userGold = userDao.doSelectOne(userWithLevel);// GOLD
		UserDTO userBASIC = userDao.doSelectOne(userWithOutLevel);// BASIC

		assertEquals(userGold.getGrade(), Level.GOLD);
		assertEquals(userBASIC.getGrade(), Level.BASIC);

	}

	@Disabled
	@Test
	public void upgradeLevels() throws SQLException {
		// 매번 동일한 결과가 도출 되도록 지정
		// 1. 전체 삭제
		// 2. 5명 사용자 모두 입력
		// 3. 등업
		// 4. 데이터로 비교
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
		userService.upgradeLevels();

		// 4.
		// BASIC

		checkLevel(users.get(0), false);
		checkLevel(users.get(1), true);
		checkLevel(users.get(2), false);
		checkLevel(users.get(3), true);
		checkLevel(users.get(4), false);

		// checkLevel(UserDTO user, boolean upgraded)

	}

	/**
	 * 등업 판단
	 * 
	 * @param user
	 * @param upgraded
	 * @throws SQLException
	 */
	private void checkLevel(UserDTO user, boolean upgraded) throws SQLException {
		UserDTO upgradeUser = userDao.doSelectOne(user);

		// 등업
		if (true == upgraded) {
			assertEquals(upgradeUser.getGrade(), user.getGrade().getNetLevel());
		} // No 등업
		else {
			assertEquals(upgradeUser.getGrade(), user.getGrade());
		}

	}

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
		log.debug("transactionManager :" + transactionManager);
		log.debug("mailSender :" + mailSender);
	}

}

