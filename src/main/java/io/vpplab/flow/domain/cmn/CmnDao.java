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
    int getAgencyBusiChk(HashMap<String, Object> paramMap);
    int setPltfMngSave(HashMap<String, Object> paramMap);
    int setPltfMngDel(HashMap<String, Object> paramMap);
    int setAgencyAdd(HashMap<String, Object> paramMap);
    int setAuthorizationAdd(HashMap<String, Object> paramMap);
    int setauthMenuMappingAdd(HashMap<String, Object> paramMap);
    int setCodeSave(HashMap<String, Object> paramMap);
    List<HashMap> getCodeList(HashMap<String, Object> paramMap);
    List<HashMap> getCodeDtl(HashMap<String, Object> paramMap);
}
