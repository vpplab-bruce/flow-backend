package io.vpplab.flow.domain.prdc;


import java.util.HashMap;
import java.util.List;

public interface PrdcDao {
    List<HashMap> getPrdcAnlyList(HashMap<String, Object> paramMap);
    int getPrdcAnlyListCnt(HashMap<String, Object> paramMap);
    HashMap getPrdcAnlyInfo(HashMap<String, Object> paramMap);
    HashMap getPrdcAnlyRsrInfo(HashMap<String, Object> paramMap);
    HashMap getPrdcAnlyDtl(HashMap<String, Object> paramMap);
    List<HashMap> tab1PrdcList01(HashMap<String, Object> paramMap);
    List<HashMap> tab1PrdcList02(HashMap<String, Object> paramMap);
    List<HashMap> tab1PrdcList03(HashMap<String, Object> paramMap);
    List<HashMap> tab2PrdcList(HashMap<String, Object> paramMap);
    int tab2PrdcListCnt(HashMap<String, Object> paramMap);
}
