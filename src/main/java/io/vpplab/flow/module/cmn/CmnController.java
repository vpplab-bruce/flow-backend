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


/*
 frontend 파라미터 전달  예)
 var axios = require('axios');
 var FormData = require('form-data');
 var data = new FormData();
 data.append('로그인ID', 'donghae');*/


    @RequestMapping("/cmn/getLogin")
    public HashMap getLogin(@RequestParam HashMap<String,String> paramMap) {
        return cmnService.getLogin(paramMap);
    }

    @RequestMapping("/cmn/getMenuMng")
    public Map<String, Object> getMenuMng(@RequestParam HashMap<String,String> paramMap) {
        return cmnService.getMenuMng(paramMap);
    }

}
