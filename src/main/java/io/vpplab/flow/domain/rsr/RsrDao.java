package io.vpplab.flow.domain.rsr;

import java.util.HashMap;
import java.util.List;

public interface RsrDao {
    List<HashMap> getClcRsrList(HashMap<String, Object> paramMap);
    int getClcRsrListCnt(HashMap<String, Object> paramMap);
    int setClcRsr(HashMap<String, Object> paramMap);
    HashMap getClcRsrDtl(HashMap<String, Object> paramMap);
    List<HashMap> getClcRsrMemoList(HashMap<String, Object> paramMap);
    HashMap getRsrInfo(HashMap<String, Object> paramMap);
    List<HashMap> tab1List(HashMap<String, Object> paramMap);
    List<HashMap> tab2List(HashMap<String, Object> paramMap);
    int tab2ListCnt(HashMap<String, Object> paramMap);
    int setMemo(HashMap<String, Object> paramMap);
    int setMemoDel(HashMap<String, Object> paramMap);
    int setRsrPlantDel(HashMap<String, Object> paramMap);
    List<HashMap> getRsrPlantONList(HashMap<String, Object> paramMap);
    List<HashMap> getRsrPlantList(HashMap<String, Object> paramMap);
    int getRsrPlantListCnt(HashMap<String, Object> paramMap);
    int setRsrPlant(HashMap<String, Object> paramMap);
}
