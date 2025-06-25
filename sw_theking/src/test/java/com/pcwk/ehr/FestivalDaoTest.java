package com.pcwk.ehr;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.festival.dao.FestivalDao;
import com.pcwk.ehr.festival.domain.FestivalDTO;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class FestivalDaoTest {

	Logger log = LogManager.getLogger(getClass());
	
	FestivalDTO dto01;
	FestivalDTO dto02;
	FestivalDTO dto03;
	
	@Autowired
	FestivalDao dao;
	@Autowired
	ApplicationContext context;
	@BeforeEach
	void setUp() throws Exception {
		dto01 =new FestivalDTO(1, "축제1","축제 시작1" , "축제가 시작됩니다.", "경기도 고양시", "010-1234-1234",
				10000, 41280, "2025-06-12", "2025-07-12");
		dto02 =new FestivalDTO(2, "축제2","축제 시작2" , "축제가 시작됩니다.", "경기도 고양시", "010-1234-1234",
				10000, 41280, "2025-06-12", "2025-07-12");
		dto03 =new FestivalDTO(3, "축제3","축제 시작3" , "축제가 시작됩니다.", "경기도 고양시", "010-1234-1234",
				10000, 41280, "2025-06-12", "2025-07-12");
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("***************************");
		log.debug("@After");
		log.debug("***************************");
	}

	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(dao);
		
		log.debug("context : " + context);
		log.debug("dao : " + dao);
	}
	
	//@Test
	void deleteAnddoSave() throws SQLException {
		dao.doDelete(dto01);
		dao.doDelete(dto02);
		dao.doDelete(dto03);		
		
		dao.doSave(dto01);
		assertNotNull(dto01);
		dao.doSave(dto02);
		assertNotNull(dto02);
		dao.doSave(dto03);
		assertNotNull(dto03);
		
	}
	
	@Test
	void doSave() throws SQLException{
		dao.doSave(dto01);
		assertNotNull(dto01);
		dao.doSave(dto02);
		assertNotNull(dto02);
		dao.doSave(dto03);
		assertNotNull(dto03);
	}

}
