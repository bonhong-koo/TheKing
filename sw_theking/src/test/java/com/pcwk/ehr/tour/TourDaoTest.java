package com.pcwk.ehr.tour;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.tour.dao.TourDaoImpl;
import com.pcwk.ehr.tour.domain.TourDTO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml"
		,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"	})
class TourDaoTest {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	TourDaoImpl dao;
	
	TourDTO dto01;
	
	@Autowired
	ApplicationContext context;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("context:" + context);
		
		dto01 = new TourDTO(1,"관광지1","소제목1","상세내용1",0,"서울특별시 서대문구 123","토요일","09:00-16:00","010-1111-2222",100000,0);
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("***************************");
		log.debug("@After");
		log.debug("***************************");
	}
	
	@Disabled
	@Test
	void doSaveTour() {
		//1. 전체 삭제
		//2. 단건 등록
		
		dao.deleteAll();
		
		int flag = dao.doSaveTour(dto01);
		assertEquals(1,flag);
		
	}
	@Test
	void deleteAll() {
		dao.deleteAll();
	}
}
