package com.pcwk.ehr.festival.dao;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;

public interface FestivalDao {
	
	//축제 페이징
	List<FestivalDTO> doRetrieve(DTO param);
	//단건 삭제
	int doDelete(FestivalDTO param);
	//수정
	int doUpdate(FestivalDTO param);
	//등록
	int doSave(FestivalDTO param) throws SQLException;
	//단건 조회
	FestivalDTO doSelectOne(FestivalDTO param) throws SQLException;
	//등록 건수
	int getCount() throws SQLException;

}
