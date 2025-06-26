package com.pcwk.ehr.tour;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;

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
import com.pcwk.ehr.user.domain.UserDTO;

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
        log.debug("context: {}", context);

        dto01 = new TourDTO(0, "관광지1", "소제목1", "상세내용1", 0,
                "서울특별시 서대문구 123", "토요일", "09:00-16:00", "010-1111-2222", 100000, 0, null);
        dto02 = new TourDTO(0, "관광지2", "소제목2", "상세내용2", 0,
                "세종특별자치시 123", "토요일", "09:00-16:00", "010-1111-2223", 200000, 0, null);
        dto03 = new TourDTO(0, "관광지3", "소제목3", "상세내용3", 0,
                "세종특별자치시 풍무로", "토요일", "09:00-16:00", "010-1111-2223", 200000, 0, null);
    }

    @AfterEach
    void tearDown() throws Exception {
        log.debug("***************************");
        log.debug("@After");
        log.debug("***************************");
    }
    @Disabled
    @Test
    void doUpdate() throws SQLException {
        dao.deleteAll();

        // 1. 등록
        int flag = dao.doSave(dto01);
        assertEquals(1, flag);

        log.debug("등록된 tourNo: {}", dto01.getTourNo());

        // 2. 조회
        TourDTO searchDTO = new TourDTO();
        searchDTO.setTourNo(dto01.getTourNo());
        TourDTO outVO = dao.doSelectOne(searchDTO);
        assertNotNull(outVO);

        // 3. 수정
        String upString = "_U";
        int upInt = 999;

        outVO.setName(outVO.getName() + upString);
        outVO.setSubtitle(outVO.getSubtitle() + upString);
        outVO.setTel("9999-9999");

        flag = dao.doUpdate(outVO);
        assertEquals(1, flag);

        // 4. 검증
        TourDTO updatedVO = dao.doSelectOne(searchDTO);
        assertNotNull(updatedVO);

        assertEquals(outVO.getName(), updatedVO.getName());
        assertEquals(outVO.getSubtitle(), updatedVO.getSubtitle());
        assertEquals(outVO.getTel(), updatedVO.getTel());
    }
    @Test
    void viewsUpdate() {
    	dao.deleteAll();

        int flag = dao.doSave(dto01);
        assertEquals(1, flag);
        
        flag = dao.viewsUpdate(dto01);
        assertEquals(1, flag);
        
        TourDTO outDTO = dao.doSelectOne(dto01);
        assertNotNull(outDTO);     
        
    }
    
    @Disabled
    @Test
    void testDoSave() throws SQLException {
        dao.deleteAll();

        int flag = dao.doSave(dto01);
        assertEquals(1, flag);

        int savedTourNo = dto01.getTourNo();
        log.debug("저장된 tourNo: {}", savedTourNo);

        // 조회
        TourDTO param = new TourDTO();
        param.setTourNo(savedTourNo);

        TourDTO outDTO = dao.doSelectOne(param);
        assertNotNull(outDTO);

        int count = dao.getCount();
        assertEquals(1, count);

        // 삭제
        int deleteCount = dao.doDelete(param);
        assertEquals(1, deleteCount);
    }
    @Disabled
    @Test
    void beans() {
        assertNotNull(context);
        assertNotNull(dao);
        log.debug("context: {}", context);
        log.debug("dao: {}", dao);
    }
}