package com.pcwk.ehr.tour;

import static org.junit.Assert.assertNotNull;
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
	TourDTO dto02;
	TourDTO dto03;
	
	@Autowired
	ApplicationContext context;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("context:" + context);
		
		dto01 = new TourDTO(1,"관광지1","소제목1","상세내용1",0,"서울특별시 서대문구 123","토요일","09:00-16:00","010-1111-2222",100000,0);
		dto02 = new TourDTO(2,"관광지2","소제목2","상세내용2",0,"세종특별자치시 123","토요일","09:00-16:00","010-1111-2223",200000,0);
		dto03 = new TourDTO(3,"관광지3","소제목3","상세내용3",0,"세종특별자치시 풍무로","토요일","09:00-16:00","010-1111-2223",200000,0);
	
	
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("***************************");
		log.debug("@After");
		log.debug("***************************");
	}
	@Test
	public void testDoSaveTour() {
		TourDTO dto = new TourDTO();
		dto.setName("남산타워");
		dto.setSubtitle("서울의 랜드마크");
		dto.setContents("남산서울타워입니다.");
		dto.setViews(0);
		dto.setAddress("서울특별시 용산구");
		dto.setHoliday("연중무휴");
		dto.setTime("09:00 ~ 23:00");
		dto.setTel("02-345-6789");
		dto.setFee(10000);

	    int flag = dao.doSaveTour(dto);
	    assertEquals(1, flag);
	}
	
	@Disabled
	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(dao);
		
		log.debug(context);
		log.debug(dao);
		
	}

}
