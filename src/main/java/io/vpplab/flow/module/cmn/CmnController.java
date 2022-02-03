package io.vpplab.flow.module.cmn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class CmnController {

    @Autowired
    private CmnService cmnService;

    @RequestMapping("/getLogin")
    public HashMap getLogin(@RequestParam HashMap<String,String> paramMap) {
        return cmnService.getLogin(paramMap);
    }

}
