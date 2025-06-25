package com.pcwk.ehr.festival.dao;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.festival.domain.FestivalDTO;

@Repository
public class FestivalDaoImpl implements FestivalDao, PLog{
	
	final String NAMESPACE = "com.pcwk.ehr.festivalMapper";
	final String DOT       = ".";
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate; //DB연결, SQL수행, 자원반납
	
	public FestivalDaoImpl() {}

	@Override
	public List<FestivalDTO> doRetrieve(DTO param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//단건 삭제
	@Override
	public int doDelete(FestivalDTO param) {
		int flag;
		log.debug("1.param:{}", param.toString());
		
		String statement = NAMESPACE + DOT + "doDelete";
		log.debug("statement :{}",statement);
		
		flag = sqlSessionTemplate.delete(statement, param);
		log.debug("flag :{}",flag);

		return flag;
	}

	@Override
	public int doUpdate(FestivalDTO param) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	//축제 단건 등록
	@Override
	public int doSave(FestivalDTO param) throws SQLException {
		int flag = 0;
		
		String statement = NAMESPACE + DOT + "doSave";
		log.debug("statement :{}",statement);
		
		flag = sqlSessionTemplate.insert(statement,param);
		log.debug("flag :{}",flag);
		return flag;
	}

	@Override
	public FestivalDTO doSelectOne(FestivalDTO param) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
