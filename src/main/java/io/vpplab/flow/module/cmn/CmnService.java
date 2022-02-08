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

    public HashMap getLogin(HashMap<String,String> paramMap) {
       //  paramMap.put("로그인ID","donghae");
        HashMap<String,String> hashMap  =  cmnDao.getLogin(paramMap);
        if(hashMap == null){
            hashMap = new HashMap<>();
            hashMap.put("상태","01");
        }else{
            hashMap.put("상태","00");
        }
        return hashMap;
    }


    public Map<String, Object> getMenuMng(HashMap<String,String> paramMap) {
        Map<String, Object> menuMap = new HashMap<>();
        List<Map<String, Object>> menuTree = new ArrayList<Map<String, Object>>();
        List<HashMap> menuList  =  cmnDao.getMenuMng(paramMap);

        int cnt =0;
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

            menuMap.put("1차메뉴",menuTree);
            menuMap.put("상태","00");
        }else{
            menuMap.put("상태","01");
        }
        return menuMap;
    }
}
