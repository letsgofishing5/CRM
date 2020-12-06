package com.cth.crm.workbench.service;

import com.cth.crm.workbench.domain.Clue;
import com.cth.crm.workbench.domain.Tran;

public interface ClueService {
    boolean saveClue(Clue clue);

    Clue getUserInfo(String id);

    boolean unbound(String id);

    boolean bound(String cid, String[] ids);

    boolean convert(String clueId, Tran t, String createBy);
}
