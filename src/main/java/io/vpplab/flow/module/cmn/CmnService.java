package io.vpplab.flow.module.cmn;

import io.vpplab.flow.domain.cmn.CmnDao;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@MapperScan(basePackages = "io.vpplab.flow.domain")
public class CmnService {

    @Autowired
    private CmnDao cmnDao;

    public Map<String, Object> getLogin(HashMap<String,String> paramMap) {
        HashMap<String,String> loginMap  =  cmnDao.getLogin(paramMap);
        Map<String, Object> multiMap = new HashMap<>();
        List<Map<String, Object>> menuTree = new ArrayList<Map<String, Object>>();
        List<HashMap> menuList  =  cmnDao.getMenuMng(paramMap);

        int cnt =0;
        if(loginMap != null){
            if(menuList.size() > 0){
                for(int i = 0; i < menuList.size(); i++){
                    if(!"".equals(menuList.get(i).get("1차메뉴")) && menuList.get(i).get("1차메뉴") != null){
                        Map<String, Object> dep1Map = new HashMap<>();
                        dep1Map.put( "URL주소",menuList.get(i).get("URL주소"));
                        dep1Map.put( "화면이름",menuList.get(i).get("화면이름"));
                        dep1Map.put( "화면여부",menuList.get(i).get("화면여부"));
                        dep1Map.put( "수정권한여부",menuList.get(i).get("수정권한여부"));
                        menuTree.add(cnt,dep1Map);
                        List<HashMap> sub  =  new ArrayList<>();
                        for(int j = 0; j < menuList.size(); j++){
                            if((menuList.get(i).get("1차메뉴")).equals(menuList.get(j).get("2차메뉴")) && ("".equals(menuList.get(j).get("1차메뉴")) || menuList.get(j).get("1차메뉴") == null)){
                                HashMap dep2Map = new HashMap<String,Object>();
                                dep2Map.put( "URL주소",menuList.get(j).get("URL주소"));
                                dep2Map.put( "화면이름",menuList.get(j).get("화면이름"));
                                dep2Map.put( "화면여부",menuList.get(j).get("화면여부"));
                                dep2Map.put( "수정권한여부",menuList.get(j).get("수정권한여부"));
                                sub.add(dep2Map);
                            }
                        }
                        menuTree.get(cnt).put("2차메뉴",sub);
                        cnt++;
                    }
                }

                multiMap.put("1차메뉴",menuTree);
            }
            multiMap.put("로그인정보",loginMap);
            multiMap.put("조회여부","1");

        }else{
            multiMap.put("로그인정보",null);
            multiMap.put("1차메뉴",null);
            multiMap.put("조회여부","0");
        }
        return multiMap;
    }


}
