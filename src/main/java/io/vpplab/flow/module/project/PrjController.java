package io.vpplab.flow.module.project;

import io.vpplab.flow.module.cmn.CmnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PrjController {

    @Autowired
    private PrjService prjService;

    @RequestMapping("/prj/getMyInfo")
    public Map<String, Object> getMyInfo(@RequestParam HashMap<String,Object> paramMap, HttpServletRequest request) {
        return prjService.getMyInfo(paramMap,request);
    }

}
