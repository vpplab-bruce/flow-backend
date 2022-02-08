package io.vpplab.flow.module.rsr;

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
public class RsrController {

    @Autowired
    private RsrService rsrService;





    @RequestMapping("/rsr/getClcRsrList")
    public Map<String, Object> getClcRsrList(@RequestParam HashMap<String,Object> paramMap, HttpServletRequest request) {
        return rsrService.getClcRsrList(paramMap,request);
    }

}
