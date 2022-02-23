package io.vpplab.flow.module.cmn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CmnController {

    @Autowired
    private CmnService cmnService;





    @RequestMapping("/cmn/getLogin")
    public Map<String, Object> getLogin(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return cmnService.getLogin(paramMap,request);
    }


    @RequestMapping("/cmn/setPswdInit")
    public Map<String, Object> setPswdInit(@RequestBody HashMap<String,Object> paramMap,HttpServletRequest request) {
        return cmnService.setPswdInit(paramMap,request);
    }
    @RequestMapping("/cmn/getPltfMng")
    public Map<String, Object> getPltfMng(@RequestBody HashMap<String,Object> paramMap,HttpServletRequest request) {
        return cmnService.getPltfMng(paramMap,request);
    }
    @RequestMapping("/cmn/getPltfMngDtl")
    public Map<String, Object> getPltfMngDtl(@RequestBody HashMap<String,Object> paramMap,HttpServletRequest request) {
        return cmnService.getPltfMngDtl(paramMap,request);
    }
    @RequestMapping("/cmn/getAgencyBusiChk")
    public Map<String, Object> getAgencyBusiChk(@RequestBody HashMap<String,Object> paramMap,HttpServletRequest request) {
        return cmnService.getAgencyBusiChk(paramMap,request);
    }
    @RequestMapping("/cmn/setPltfMngSave")
    public Map<String, Object> setPltfMngSave(@RequestBody HashMap<String,Object> paramMap,HttpServletRequest request) {
        return cmnService.setPltfMngSave(paramMap,request);
    }
    @RequestMapping("/cmn/setPltfMngDel")
    public Map<String, Object> setPltfMngDel(@RequestBody HashMap<String,Object> paramMap,HttpServletRequest request) {
        return cmnService.setPltfMngDel(paramMap,request);
    }
}
