package com.cth.crm.workbench.dao;

import com.cth.crm.workbench.domain.Clue;

public interface ClueDao {


    int saveClue(Clue clue);

    Clue getUserInfo(String id);

    Clue getClueById(String clueId);

    int delete(String clueId);
}
