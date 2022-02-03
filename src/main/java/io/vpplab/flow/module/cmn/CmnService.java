package io.vpplab.flow.module.cmn;

import io.vpplab.flow.domain.cmn.CmnDao;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
@MapperScan(basePackages = "io.vpplab.flow.domain")
public class CmnService {

    @Autowired
    private CmnDao cmnDao;

    public HashMap getLogin(HashMap<String,String> paramMap) {
        // paramMap.put("로그인ID","donghae");
        HashMap<String,String> hashMap  =  cmnDao.getLogin(paramMap);
        if(hashMap == null){
            hashMap = new HashMap<>();
            hashMap.put("상태","01");
        }else{
            hashMap.put("상태","00");
        }
        return hashMap;
    }

}
