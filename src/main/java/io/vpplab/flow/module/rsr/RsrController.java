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
    @RequestMapping("/rsr/getClcRsrDtlList")
    public Map<String, Object> getClcRsrDtlList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getClcRsrDtlList(paramMap,request);
    }
    @RequestMapping("/rsr/setClcRsrSave")
    public Map<String, Object> setClcRsrSave(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setClcRsrSave(paramMap,request);
    }
    @RequestMapping("/rsr/setClcRsrDel")
    public Map<String, Object> setClcRsrDel(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setClcRsrDel(paramMap,request);
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
    @RequestMapping("/rsr/getRsrPlantSchList")
    public Map<String, Object> getRsrPlantSchList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getRsrPlantSchList(paramMap,request);
    }
    @RequestMapping("/rsr/setRsrPlant")
    public Map<String, Object> setRsrPlant(@RequestBody List<HashMap<String,Object>> paramMap, HttpServletRequest request) {
        return rsrService.setRsrPlant(paramMap,request);
    }
    @RequestMapping("/rsr/setRsrPlantListDel")
    public Map<String, Object> setRsrPlantListDel(@RequestBody List<HashMap<String,Object>> paramMap, HttpServletRequest request) {
        return rsrService.setRsrPlantListDel(paramMap,request);
    }

    //???????????????????????? ??????
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
    @RequestMapping("/rsr/getPlantBusiRsrAdd")
    public Map<String, Object> getPlantBusiRsrAdd(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getPlantBusiRsrAdd(paramMap,request);
    }
    @RequestMapping("/rsr/getPlantBusiRsrDtl")
    public Map<String, Object> getPlantBusiRsrDtl(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getPlantBusiRsrDtl(paramMap,request);
    }
    @RequestMapping("/rsr/setPlantBusiRsrSave")
    public Map<String, Object> setPlantBusiRsrSave(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setPlantBusiRsrSave(paramMap,request);
    }
    @RequestMapping("/rsr/setPlantBusiRsrDel")
    public Map<String, Object> setPlantBusiRsrDel(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setPlantBusiRsrDel(paramMap,request);
    }

    //????????????
    @RequestMapping("/rsr/getPtrRsrList")
    public Map<String, Object> getPtrRsrList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getPtrRsrList(paramMap,request);
    }
    @RequestMapping("/rsr/getPtrRsrListPop")
    public Map<String, Object> getPtrRsrListPop(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getPtrRsrListPop(paramMap,request);
    }
    @RequestMapping("/rsr/getPtrRsrAddDtl")
    public Map<String, Object> getPtrRsrAddDtl(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getPtrRsrAddDtl(paramMap,request);
    }

    @RequestMapping("/rsr/setPtrRsrAddSave")
    public Map<String, Object> setPtrRsrAddSave(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setPtrRsrAddSave(paramMap,request);
    }
    @RequestMapping("/rsr/setPtrRsrAddDel")
    public Map<String, Object> setPtrRsrAddDel(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setPtrRsrAddDel(paramMap,request);
    }
    @RequestMapping("/rsr/getPtrRsrDtl")
    public Map<String, Object> getPtrRsrDtl(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getPtrRsrDtl(paramMap,request);
    }
    @RequestMapping("/rsr/getPtrRsrDtlList")
    public Map<String, Object> getPtrRsrDtlList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getPtrRsrDtlList(paramMap,request);
    }
    @RequestMapping("/rsr/getClcRsrMemoList")
    public Map<String, Object> getClcRsrMemoList(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getClcRsrMemoList(paramMap,request);
    }

    @RequestMapping("/rsr/setClcRsrMemo")
    public Map<String, Object> setClcRsrMemo(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setClcRsrMemo(paramMap,request);
    }
    @RequestMapping("/rsr/setClcRsrMemoDel")
    public Map<String, Object> setClcRsrMemoDel(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.setClcRsrMemoDel(paramMap,request);
    }

}
