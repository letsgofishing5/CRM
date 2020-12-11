package com.cth.crm.workbench.dao;

import com.cth.crm.workbench.domain.Clue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueDao {


    int saveClue(Clue clue);

    Clue getUserInfo(String id);

    Clue getClueById(String clueId);

    int delete(String clueId);

    int getClueTotal();

    List<Clue> getClueDataList(@Param("pageSkip") int pageSkip,@Param("pageSize") int pageSize);
}
