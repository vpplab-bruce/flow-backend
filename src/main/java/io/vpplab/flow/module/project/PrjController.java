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
    @RequestMapping("/prj/getUserList")
    public Map<String, Object> getUserList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prjService.getUserList(paramMap,request);
    }
    @RequestMapping("/prj/getUserDtl")
    public Map<String, Object> getUserDtl(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prjService.getUserDtl(paramMap,request);
    }
    @RequestMapping("/prj/setUserSave")
    public Map<String, Object> setUserSave(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prjService.setUserSave(paramMap,request);
    }

    @RequestMapping("/prj/setUserPwInit")
    public Map<String, Object> setUserPwInit(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prjService.setUserPwInit(paramMap,request);
    }
    @RequestMapping("/prj/setUserDel")
    public Map<String, Object> setUserDel(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prjService.setUserDel(paramMap,request);
    }
    @RequestMapping("/prj/setUserAdd")
    public Map<String, Object> setUserAdd(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prjService.setUserAdd(paramMap,request);
    }

}
