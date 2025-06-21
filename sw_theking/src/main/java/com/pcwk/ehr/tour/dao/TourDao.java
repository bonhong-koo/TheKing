package com.pcwk.ehr.tour.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.tour.domain.TourDTO;

public class TourDao implements PLog{
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	//1. 관광지 정보 등록 doSaveTour
	//2. 관광지 정보 수정 doUpdate
	//3. 관광지 정보 삭제 doDelete +deleteAll(테스트용)
	//4.1 관광지 시도별 조회(페이징) doRetrieve
	//4.2 관광지 시군별 조회(페이징) doRetrieve
	//4.3 관광지 검색 조회 (제목, 지역)(페이징) doSelectOne
	//5.getCount -> 몇 권인지 조회할 때
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void deleteAll() {
		StringBuilder sb = new StringBuilder(100);
		sb.append(" delete from tour \n");

		log.debug("1.sql:\n" + sb.toString());
		jdbcTemplate.update(sb.toString());		
	}

	/**
	 * tour 정보 등록
	 *@param param
	 * 1(등록 성공), 0(등록 실패)
	 */
	public int doSaveTour(TourDTO param) {
		int flag = 0;
        // 1. 전체 주소에서 시도/구군 추출
        String fullAddress = param.getAddress();
        String[] parts = fullAddress.split(" ");

        String regionSido = parts[0];
        String regionGugun = parts[1];
		
		//처음 등록될 경우 Views Default 0
		StringBuilder sb = new StringBuilder(200);
		sb.append("INSERT INTO tour (                   \n");
		sb.append("    TOUR_NO,                         \n");  //자동으로 증가 
		sb.append("    name,                            \n");  //필수값
		sb.append("    subtitle,						\n");  //필수값
		sb.append("    contents,                        \n");
		sb.append("    views,                           \n");  //증가 함수 필요 
		sb.append("    address,                         \n");  //필수값,주소API 사용 예정
		sb.append("    holiday,                         \n");
		sb.append("    time,                            \n");
		sb.append("    tel,                             \n");
		sb.append("    fee,                             \n");
		sb.append("    region_no                        \n");   //필수값
		sb.append(") VALUES (TOUR_NO_SEQ.NEXTVAL,       \n");
		sb.append("           ?,                        \n");
		sb.append("           ?,                        \n");
		sb.append("           ?,                        \n");
		sb.append("           0,                        \n");
		sb.append("           ?,                        \n");
		sb.append("           ?,                        \n");
		sb.append("           ?,                        \n");
		sb.append("           ?,                        \n");
		sb.append("           ?,                        \n");
		sb.append("           (SELECT REGION_NO         \n");
		sb.append("				FROM REGION             \n");
		sb.append("				WHERE REGION_SIDO = ?   \n");
		sb.append("				AND REGION_GUGUN = ?))  \n");
		log.debug("1.sql:\n" + sb.toString());
		
		//param 10개
		Object[] args = {param.getName(),
							param.getSubtitle(),
							param.getContents(),
							param.getAddress(),
							param.getHoliday(),
							param.getTime(),
							param.getTel(),
							param.getFee(),
							regionSido,
							regionGugun
							};
		log.debug("2.param: ");
		for (Object obj : args) {
			log.debug(obj.toString());
		}
		flag = jdbcTemplate.update(sb.toString(), args);
		log.debug("3.flag: "+flag);
		
		return flag;
	}
	
}
