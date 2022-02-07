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
                    menuTree.add(cnt,menuList.get(i));
                    List<HashMap> sub  =  new ArrayList<>();
                    for(int j = 0; j < menuList.size(); j++){
                        if((menuList.get(i).get("1차메뉴")).equals(menuList.get(j).get("2차메뉴")) && ("".equals(menuList.get(j).get("1차메뉴")) ||menuList.get(j).get("1차메뉴") == null)){
                            sub.add(menuList.get(j));
                        }

                    }
                    menuTree.get(cnt).put("서브메뉴리스트",sub);
                    cnt++;
                }
            }



            menuMap.put("메뉴리스트",menuTree);
            // menuMap.put("menuList",menuList);
            menuMap.put("상태","00");
        }else{
            menuMap.put("상태","01");
        }
        return menuMap;
    }
}
