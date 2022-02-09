package io.vpplab.flow.module.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PrjController {

    @Autowired
    private PrjService prjService;

    @RequestMapping("/prj/getMyInfo")
    public Map<String, Object> getMyInfo(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prjService.getMyInfo(paramMap,request);
    }
    @RequestMapping("/prj/setMyInfo")
    public Map<String, Object> setMyInfo(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prjService.setMyInfo(paramMap,request);
    }
    @RequestMapping("/prj/getWithdrawal")
    public Map<String, Object> getWithdrawal(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prjService.getWithdrawal(paramMap,request);
    }

}
