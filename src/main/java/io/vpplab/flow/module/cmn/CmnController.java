package io.vpplab.flow.module.cmn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CmnController {

    @Autowired
    private CmnService cmnService;





    @RequestMapping("/cmn/getLogin")
    public Map<String, Object> getLogin(@RequestParam HashMap<String,String> paramMap) {
        return cmnService.getLogin(paramMap);
    }

}
