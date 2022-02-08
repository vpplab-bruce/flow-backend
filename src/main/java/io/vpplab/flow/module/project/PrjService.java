package io.vpplab.flow.module.project;

import io.vpplab.flow.domain.cmn.CmnDao;
import io.vpplab.flow.domain.project.PrjDao;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@MapperScan(basePackages = "io.vpplab.flow.domain")
public class PrjService {

    @Autowired
    private PrjDao prjDao;

    public Map<String, Object> getMyInfo(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        if(loginInfo == null){
            multiMap.put("조회여부","0");
            return multiMap;
        }
        String userId = loginInfo.get("로그인ID").toString();
        paramMap.put("로그인ID",userId);
        HashMap<String,Object> loginMap  =  prjDao.getMyInfo(paramMap);
        if(loginMap != null){
            multiMap.put("조회여부","1");
            multiMap.put("내정보",loginMap);
        }else{
            multiMap.put("조회여부","0");
        }

        return multiMap;
    }

}
