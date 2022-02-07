package io.vpplab.flow.module.rsr;

import io.vpplab.flow.module.cmn.CmnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class RsrController {

    @Autowired
    private RsrService rsrService;





    @RequestMapping("/getClcRsrList")
    public List<HashMap> getClcRsrList(@RequestParam HashMap<String,String> paramMap) {
        return rsrService.getClcRsrList(paramMap);
    }

}
