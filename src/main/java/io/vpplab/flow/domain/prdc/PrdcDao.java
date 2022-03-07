package io.vpplab.flow.domain.prdc;


import java.util.HashMap;
import java.util.List;

public interface PrdcDao {
    List<HashMap> getPrdcAnlyList(HashMap<String, Object> paramMap);
    int getPrdcAnlyListCnt(HashMap<String, Object> paramMap);
    HashMap getPrdcAnlyInfo(HashMap<String, Object> paramMap);
}
