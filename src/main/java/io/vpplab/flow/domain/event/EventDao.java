package io.vpplab.flow.domain.event;

import java.util.HashMap;
import java.util.List;

public interface EventDao {

    List<HashMap> getEventList(HashMap<String, Object> paramMap);
    int getEventListCnt(HashMap<String, Object> paramMap);
    HashMap getEventDtl(HashMap<String, Object> paramMap);
    int setEventSave(HashMap<String, Object> paramMap);
}
