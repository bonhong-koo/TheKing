package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.user.domain.UserDTO;

@Mapper
public interface FestivalMapper extends WorkDiv<FestivalDTO>{

	int saveAll();
	
	List<FestivalDTO> getAll();
	
	void deleteAll() throws SQLException;
	
	int getCount() throws SQLException;

}
