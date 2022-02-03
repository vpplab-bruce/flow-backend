package io.vpplab.flow.domain.cmn;

import java.util.HashMap;

public interface CmnDao {
    HashMap getLogin(HashMap<String, String> paramMap);
}
