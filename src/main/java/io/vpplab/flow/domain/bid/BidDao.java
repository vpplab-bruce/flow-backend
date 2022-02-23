package io.vpplab.flow.domain.bid;

import java.util.HashMap;
import java.util.List;

public interface BidDao {
    List<HashMap> getBidList(HashMap<String, Object> paramMap);
    int getBidListCnt(HashMap<String, Object> paramMap);
}
