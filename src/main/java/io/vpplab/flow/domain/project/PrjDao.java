package io.vpplab.flow.domain.project;

import java.util.HashMap;
import java.util.List;

public interface PrjDao {
    HashMap getMyInfo(HashMap<String, Object> paramMap);
    int setMyInfo(HashMap<String, Object> paramMap);
    int getWithdrawal(HashMap<String, Object> paramMap);
    List<HashMap> getUserList(HashMap<String, Object> paramMap);
    int getUserListCnt(HashMap<String, Object> paramMap);
    HashMap getUserDtl(HashMap<String, Object> paramMap);
    int setUserSave(HashMap<String, Object> paramMap);
    int setUserPswd(HashMap<String, Object> paramMap);
    int setUserDel(HashMap<String, Object> paramMap);
    int setUserAdd(HashMap<String, Object> paramMap);
}