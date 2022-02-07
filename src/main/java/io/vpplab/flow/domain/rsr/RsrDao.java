package io.vpplab.flow.domain.rsr;

import java.util.HashMap;
import java.util.List;

public interface RsrDao {
    List<HashMap> getClcRsrList(HashMap<String, String> paramMap);
}
