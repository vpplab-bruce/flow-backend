package io.vpplab.flow.domain.cmn;

import java.util.HashMap;
import java.util.List;

public interface CmnDao {
    HashMap getLogin(HashMap<String, Object> paramMap);
    HashMap getMyLoginInfo(HashMap<String, Object> paramMap);
    int setMyPswd(HashMap<String, Object> paramMap);
    List<HashMap> getMenuMng(HashMap<String, Object> paramMap);
    List<HashMap> getAdminCode();
    String getMsg(HashMap<String, Object> paramMap);
    List<HashMap> getPltfMng(HashMap<String, Object> paramMap);
    int getPltfMngCnt(HashMap<String, Object> paramMap);
    HashMap getPltfMngDtl(HashMap<String, Object> paramMap);
}
