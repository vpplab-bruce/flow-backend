package io.vpplab.flow.module.bid;

import io.vpplab.flow.module.cmn.CmnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BidController {

    @Autowired
    private BidService bidService;





    @RequestMapping("/bid/getBidList")
    public Map<String, Object> getBidList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return bidService.getBidList(paramMap,request);
    }
    @RequestMapping("/bid/getSettlementList")
    public Map<String, Object> getSettlementList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return bidService.getSettlementList(paramMap,request);
    }
}
