package com.pcwk.ehr.tour.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.region.dao.RegionDao;
import com.pcwk.ehr.region.domain.RegionDTO;
import com.pcwk.ehr.tour.domain.TourDTO;

@Repository
public class TourDaoImpl implements PLog{
	
	final String NAMESPACE = "com.pcwk.ehr.tour";
	final String DOT = ".";
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate; //DB 연결, sql 수행, 자원 반납
	
	@Autowired
	RegionDao regionDao;
	
	//1. 관광지 정보 등록 doSaveTour
	//2. 관광지 정보 수정 doUpdate
	//3. 관광지 정보 삭제 doDelete +deleteAll(테스트용)
	//4.1 관광지 시도별 조회(페이징) doRetrieve
	//4.2 관광지 시군별 조회(페이징) doRetrieve
	//4.3 관광지 검색 조회 (제목, 지역)(페이징) doSelectOne
	//5.getCount -> 몇 권인지 조회할 때
	
	
	public void deleteAll() {
		String statement = NAMESPACE+DOT+"deleteAll";
		log.debug("1 statement: {}"+statement);
		
		int flag = sqlSessionTemplate.delete(statement);
		log.debug("2 flag: {}"+flag);
		
	}
	public Integer getRegionNo(String sido, String gugun) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("regionSido", sido);
	    params.put("regionGugun", gugun);

	    String statement = NAMESPACE + DOT + "getRegionNo";
	    return sqlSessionTemplate.selectOne(statement, params);
	}

	/**
	 * tour 정보 등록
	 *@param param
	 * 1(등록 성공), 0(등록 실패)
	 */
	public int doSaveTour(TourDTO param) {
		int flag = 0;
	    
	    // 1. 주소 파싱 → 시/도, 구/군 추출
	    String fullAddress = param.getAddress();
	    String[] parts = fullAddress.split(" ");
	    
	    String regionSido = null;
	    String regionGugun = null;

	    if (parts.length > 0 && "세종특별자치시".equals(parts[0])) {
	        regionSido = parts[0];
	        regionGugun = null;
	    } else if (parts.length > 1) {
	        regionSido = parts[0];
	        regionGugun = parts[1];
	    }

	    // 2. region_no 조회
	    Integer regionNo = getRegionNo(regionSido, regionGugun);

	    // 3. TourDTO에 region 설정
	    RegionDTO region = new RegionDTO();
	    region.setRegionNo(regionNo);
	    region.setRegionSido(regionSido);
	    region.setRegionGugun(regionGugun);
	    param.setRegion(region); // ★ 핵심

	    // 4. insert
	    String statement = NAMESPACE + DOT + "doSaveTour";
	    flag = sqlSessionTemplate.insert(statement, param);

	    return flag;
	}
	
}
