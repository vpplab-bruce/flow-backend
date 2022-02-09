package io.vpplab.flow.domain.project;

import java.util.HashMap;
import java.util.List;

public interface PrjDao {
    HashMap getMyInfo(HashMap<String, Object> paramMap);
    int setMyInfo(HashMap<String, Object> paramMap);
    int getWithdrawal(HashMap<String, Object> paramMap);
}
