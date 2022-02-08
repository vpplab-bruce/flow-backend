package io.vpplab.flow.domain.cmn;

import java.util.HashMap;
import java.util.List;

public interface CmnDao {
    HashMap getLogin(HashMap<String, String> paramMap);
    HashMap getMyLoginInfo(HashMap<String, String> paramMap);
    int setMyPswd(HashMap<String, String> paramMap);
    List<HashMap> getMenuMng(HashMap<String, String> paramMap);
}
