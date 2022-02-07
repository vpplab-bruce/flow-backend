package io.vpplab.flow.domain.cmn;

import java.util.HashMap;
import java.util.List;

public interface CmnDao {
    HashMap getLogin(HashMap<String, String> paramMap);
    List<HashMap> getMenuMng(HashMap<String, String> paramMap);
}
