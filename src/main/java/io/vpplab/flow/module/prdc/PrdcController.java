package io.vpplab.flow.module.prdc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PrdcController {

    @Autowired
    private PrdcService prdcService;


    @RequestMapping("/prdc/getPrdcAnlyList")
    public Map<String, Object> getPrdcAnlyList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prdcService.getPrdcAnlyList(paramMap,request);
    }

    @RequestMapping("/prdc/getPrdcAnlyDtl")
    public Map<String, Object> getPrdcAnlyDtl(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prdcService.getPrdcAnlyDtl(paramMap,request);
    }

    @RequestMapping("/prdc/setPrdcAnlyAdd")
    public Map<String, Object> setPrdcAnlyAdd(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prdcService.setPrdcAnly(paramMap,request);
    }
    @RequestMapping("/prdc/setPrdcAnlySave")
    public Map<String, Object> setPrdcAnlySave(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prdcService.setPrdcAnlySave(paramMap,request);
    }
    @RequestMapping("/prdc/setPrdcAnlyDel")
    public Map<String, Object> setPrdcAnlyDel(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prdcService.setPrdcAnlyDel(paramMap,request);
    }
}
