package io.vpplab.flow.module.bid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping(value = "/bid/getSettlementExcelList", method = {RequestMethod.GET})
    @ResponseBody
    public void getSettlementExcelList(HttpServletRequest request, HttpServletResponse response) {
         bidService.getSettlementExcelList(request,response);
    }
}
