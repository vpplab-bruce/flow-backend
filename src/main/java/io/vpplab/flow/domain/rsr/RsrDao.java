package io.vpplab.flow.domain.rsr;

import java.util.HashMap;
import java.util.List;

public interface RsrDao {
    List<HashMap> getClcRsrList(HashMap<String, Object> paramMap);
    int getClcRsrListCnt(HashMap<String, Object> paramMap);
    int setClcRsr(HashMap<String, Object> paramMap);
    HashMap getClcRsrInfo(HashMap<String, Object> paramMap);
    HashMap getClcRsrDtl(HashMap<String, Object> paramMap);
    List<HashMap> getClcRsrMemoList(HashMap<String, Object> paramMap);
    int getClcRsrMemoListCnt(HashMap<String, Object> paramMap);
    HashMap getRsrInfo(HashMap<String, Object> paramMap);
    List<HashMap> tab1List01(HashMap<String, Object> paramMap);
    List<HashMap> tab1List02(HashMap<String, Object> paramMap);
    List<HashMap> tab1List03(HashMap<String, Object> paramMap);
    List<HashMap> tab2List(HashMap<String, Object> paramMap);
    int tab2ListCnt(HashMap<String, Object> paramMap);
    int setMemo(HashMap<String, Object> paramMap);
    int setClcRsrMemo(HashMap<String, Object> paramMap);
    int setClcRsrMemoDel(HashMap<String, Object> paramMap);
    int setMemoDel(HashMap<String, Object> paramMap);
    int setRsrPlantDel(HashMap<String, Object> paramMap);
    List<HashMap> getRsrPlantONList(HashMap<String, Object> paramMap);
    List<HashMap> getRsrPlantList(HashMap<String, Object> paramMap);
    int getRsrPlantListCnt(HashMap<String, Object> paramMap);
    int setRsrPlant(HashMap<String, Object> paramMap);

    List<HashMap> getPlantBusiList(HashMap<String, Object> paramMap);
    int getPlantBusiListCnt(HashMap<String, Object> paramMap);
    int getBusiChk(HashMap<String, Object> paramMap);
    HashMap getPlantBusiDtl(HashMap<String, Object> paramMap);
    int setPlantBusiDel(HashMap<String, Object> paramMap);
    int setPlantBusiAdd(HashMap<String, Object> paramMap);
    int setPlantBusiSave(HashMap<String, Object> paramMap);
    List<HashMap> getPlantBusiRsrList(HashMap<String, Object> paramMap);
    int getPlantBusiRsrListCnt(HashMap<String, Object> paramMap);
    int getPlantBusiRsrAdd(HashMap<String, Object> paramMap);
    int getPlantBusiRsrMetaAdd(HashMap<String, Object> paramMap);
    HashMap getPlantBusiRsrDtl(HashMap<String, Object> paramMap);
    int setPlantBusiRsrSave(HashMap<String, Object> paramMap);
    int setPlantBusiRsrMetaSave(HashMap<String, Object> paramMap);
    int setPlantBusiRsrDel(HashMap<String, Object> paramMap);
    int setPlantBusiRsrMetaDel(HashMap<String, Object> paramMap);
    List<HashMap> getPtrRsrList(HashMap<String, Object> paramMap);
    int getPtrRsrListCnt(HashMap<String, Object> paramMap);
    List<HashMap> getPtrRsrListPop(HashMap<String, Object> paramMap);
    int getPtrRsrListPopCnt(HashMap<String, Object> paramMap);
    HashMap getPtrRsrAddDtl(HashMap<String, Object> paramMap);
    int setPtrRsrAddSave(HashMap<String, Object> paramMap);
    int setPtrRsrAddDel(HashMap<String, Object> paramMap);
    HashMap getPtrRsrDtl(HashMap<String, Object> paramMap);
    List<HashMap> getPtrRsrDtlList(HashMap<String, Object> paramMap);

}
