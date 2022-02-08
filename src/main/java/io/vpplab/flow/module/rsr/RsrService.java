package io.vpplab.flow.module.rsr;

import io.vpplab.flow.domain.cmn.CmnDao;
import io.vpplab.flow.domain.rsr.RsrDao;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@MapperScan(basePackages = "io.vpplab.flow.domain")
public class RsrService {

    @Autowired
    private RsrDao rsrDao;

    public Map<String, Object> getClcRsrList(HashMap<String,String> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();
        HashMap<String,String> loginInfo = (HashMap) session.getAttribute("사용자정보");
        if(loginInfo == null){
            multiMap.put("조회여부","0");
            return multiMap;
        }
        String userId = loginInfo.get("로그인ID");
        paramMap.put("로그인ID",userId);
        List<HashMap> clcRsrMap  =  rsrDao.getClcRsrList(paramMap);
        if(clcRsrMap.size() > 0){
            multiMap.put("조회여부","1");
            multiMap.put("집합자원",clcRsrMap);
        }else{
            multiMap.put("조회여부","0");
        }
        return multiMap;
    }

}
