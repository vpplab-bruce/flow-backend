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
    int setPrdcAnly(HashMap<String, Object> paramMap);
    int setPrdcAnlyMeno(HashMap<String, Object> paramMap);
    int setPrdcAnlySave(HashMap<String, Object> paramMap);
    int setPrdcAnlyMenoSave(HashMap<String, Object> paramMap);
    int setPrdcAnlyDel(HashMap<String, Object> paramMap);
    int setPrdcAnlyListDel(HashMap<String, Object> paramMap);
    int setPrdcAnlyMenoDel(HashMap<String, Object> paramMap);
    List<HashMap> getPrdcAnlyPlantONList(HashMap<String, Object> paramMap);
    List<HashMap> getPrdcAnlyPlantList(HashMap<String, Object> paramMap);
    int getPrdcAnlyPlantListCnt(HashMap<String, Object> paramMap);
    int setPrdcAnlyPlant(HashMap<String, Object> paramMap);
    int setPrdcAnlyPlantDel(HashMap<String, Object> paramMap);

}

