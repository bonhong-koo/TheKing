package com.pcwk.ehr.tour.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.region.dao.RegionDao;
import com.pcwk.ehr.region.domain.RegionDTO;
import com.pcwk.ehr.tour.domain.TourDTO;

@Repository
public class TourDaoImpl{
	
    private static final Logger log = LogManager.getLogger(TourDaoImpl.class);

    private final String NAMESPACE = "com.pcwk.ehr.tour";
    private final String DOT = ".";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private RegionDao regionDao;
	
	//1. 관광지 정보 등록 doSave 0
	//2. 관광지 정보 수정 doUpdate 0
	//3. 관광지 정보 삭제 doDelete 0
	//+deleteAll(테스트용)0
	//4.1 관광지 시도별 조회(페이징) doRetrieve
	//4.3 관광지 검색 조회 (제목, 지역)(페이징) doSelectOne -> getRegionNo 0
	//5.getCount -> 몇 권인지 조회할 때 0
    //6.조회수 업데이트 views update
	   // 관광지 정보 조회
    public int saveAll() {
		int flag =0;
		
		String statement = NAMESPACE+DOT+"saveAll";
		flag = sqlSessionTemplate.insert(statement);
		log.debug("statement: "+statement);
		log.debug("flag: "+flag);
		
		return flag;
	}
    public List<TourDTO>doRetrieve(TourDTO param) {
		List<TourDTO> list = new ArrayList<TourDTO>();
		
		//SearchDTO searchDTO =(SearchDTO) param;
		String statement = NAMESPACE+DOT+"doRetrieve";
		log.debug("1 param: {}",param);
		log.debug("2 statement: {}",statement);

		list = sqlSessionTemplate.selectList(statement, param);
		log.debug("3 list: {}",list);
		return list;
	}
    
    public int viewsUpdate(TourDTO param) {
    	int flag = 0;
    	String statement = NAMESPACE + DOT + "viewsUpdate";
    	log.debug("1.statement:{}", statement);
    	
    	flag = sqlSessionTemplate.update(statement, param);
    	log.debug("2.flag:{}", flag);
    	return flag;
    }

    public TourDTO doSelectOne(TourDTO param) {
        TourDTO outDTO = null;
        String statement = NAMESPACE + DOT + "doSelectOne";
        outDTO = sqlSessionTemplate.selectOne(statement, param);

        if (outDTO == null) {
            throw new EmptyResultDataAccessException(param.getTourNo() + "번 관광지가 존재하지 않습니다.", 0);
        }
        return outDTO;
    }
	
	public int doDelete(TourDTO param) {
		int flag = 0;
		String statement = NAMESPACE+DOT+"doDelete";
		log.debug("1.statement:{}", statement);
		
		flag = sqlSessionTemplate.delete(statement, param);
		log.debug("2.flag:{}", flag);
		return flag;
	}
	
	public int getCount() throws SQLException {

		int count = 0;
		
		String statement = NAMESPACE+DOT+"getCount";
		log.debug("1.statement:{}", statement);

		count = sqlSessionTemplate.selectOne(statement);
		log.debug("2.count:{}", count);

		return count;
	}

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
	public int doSave(TourDTO param) {
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
	    param.setRegion(region); 

	    // 4. insert
	    String statement = NAMESPACE + DOT + "doSave";
	    flag = sqlSessionTemplate.insert(statement, param);

	    return flag;
	}

	public int doUpdate(TourDTO param) {
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
	    param.setRegion(region); 

	    // 4. update
	    String statement = NAMESPACE + DOT + "doUpdate";
	    flag = sqlSessionTemplate.update(statement, param);

	    return flag;
	}
	
}
