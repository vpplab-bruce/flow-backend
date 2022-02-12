package io.vpplab.flow.domain.rsr;

import java.util.HashMap;
import java.util.List;

public interface RsrDao {
    List<HashMap> getClcRsrList(HashMap<String, Object> paramMap);
    int getClcRsrListCnt(HashMap<String, Object> paramMap);
    int setClcRsr(HashMap<String, Object> paramMap);
    HashMap getClcRsrDtl(HashMap<String, Object> paramMap);
    List<HashMap> getClcRsrMemoList(HashMap<String, Object> paramMap);
}
