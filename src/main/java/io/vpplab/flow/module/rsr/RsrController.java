package io.vpplab.flow.module.rsr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RsrController {

    @Autowired
    private RsrService rsrService;





    @RequestMapping("/rsr/getClcRsrList")
    public Map<String, Object> getClcRsrList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getClcRsrList(paramMap,request);
    }

    @RequestMapping("/rsr/setClcRsrAdd")
    public Map<String, Object> setClcRsr(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setClcRsr(paramMap,request);
    }
    @RequestMapping("/rsr/getClcRsrDtl")
    public Map<String, Object> getClcRsrDtl(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getClcRsrDtl(paramMap,request);
    }
    @RequestMapping("/rsr/setMemo")
    public Map<String, Object> setMemo(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setMemo(paramMap,request);
    }
    @RequestMapping("/rsr/setMemoDel")
    public Map<String, Object> setMemoDel(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setMemoDel(paramMap,request);
    }
    @RequestMapping("/rsr/setRsrPlantDel")
    public Map<String, Object> setRsrPlantDel(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setRsrPlantDel(paramMap,request);
    }
    @RequestMapping("/rsr/getRsrPlantList")
    public Map<String, Object> getRsrPlantList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getRsrPlantList(paramMap,request);
    }
    @RequestMapping("/rsr/setRsrPlant")
    public Map<String, Object> setRsrPlant(@RequestBody List<HashMap<String,Object>> paramMap, HttpServletRequest request) {
        return rsrService.setRsrPlant(paramMap,request);
    }
    
    //발전자원등록관리 조회
    @RequestMapping("/rsr/getPlantBusiList")
    public Map<String, Object> getPlantBusiList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getPlantBusiList(paramMap,request);
    }
    @RequestMapping("/rsr/getBusiChk")
    public Map<String, Object> getBusiChk(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getBusiChk(paramMap,request);
    }
    @RequestMapping("/rsr/getPlantBusiDtl")
    public Map<String, Object> getPlantBusiDtl(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getPlantBusiDtl(paramMap,request);
    }
    @RequestMapping("/rsr/setPlantBusiDel")
    public Map<String, Object> setPlantBusiDel(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setPlantBusiDel(paramMap,request);
    }
    @RequestMapping("/rsr/setPlantBusiAdd")
    public Map<String, Object> setPlantBusiAdd(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setPlantBusiAdd(paramMap,request);
    }
    @RequestMapping("/rsr/setPlantBusiSave")
    public Map<String, Object> setPlantBusiSave(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setPlantBusiSave(paramMap,request);
    }
    @RequestMapping("/rsr/getPlantBusiRsrList")
    public Map<String, Object> getPlantBusiRsrList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getPlantBusiRsrList(paramMap,request);
    }
    @RequestMapping("/rsr/getPlantBusiRsrList")
    public Map<String, Object> getPlantBusiRsrAdd(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getPlantBusiRsrAdd(paramMap,request);
    }

}
