package io.vpplab.flow.module.cmn;

import io.vpplab.flow.domain.cmn.CmnDao;
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
public class CmnService {

    @Autowired
    private CmnDao cmnDao;

    public Map<String, Object> getLogin(HashMap<String,Object> paramMap,HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();
        List<Map<String, Object>> menuTree = new ArrayList<Map<String, Object>>();
        HashMap<String,Object> loginMap  =  cmnDao.getLogin(paramMap);
        List<HashMap> menuList  =  cmnDao.getMenuMng(paramMap);


        int cnt =0;
        if(loginMap != null){

            //비밀번호 비교 비밀번호실패시 비밀번호 오류건수 업데이트
            //비밀번호 비교 암호화 확인 필요


            // 비번이 맞을경우
            session.setAttribute("사용자정보", loginMap);
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
    public Map<String, Object> setPswdInit(HashMap<String,Object> paramMap,HttpServletRequest request) {

        Map<String, Object> multiMap = new HashMap<>();
        HashMap<String,Object> loginMap  =  cmnDao.getMyLoginInfo(paramMap);
        if(loginMap != null){
            String pswdInit = "";
            SecureRandom random = new SecureRandom ();
            String certNumCreate = random.nextInt(10)+""
                    + random.nextInt(10)+""
                    + random.nextInt(10)+""
                    + random.nextInt(10)+""
                    + random.nextInt(10)+""
                    + random.nextInt(10);
            pswdInit = certNumCreate;
            // pswdInit 암호화 체크
            HashMap<String, Object> infoMap = new HashMap<>();
            infoMap.put("로그인비밀번호",pswdInit);
            infoMap.put("전화번호",paramMap.get("전화번호").toString());
            infoMap.put("이메일",paramMap.get("이메일").toString());
            int cnt = cmnDao.setMyPswd(infoMap);
            if(cnt > 0){
                multiMap.put("전송여부","1");
                // SMS 전송처리
            }else{
                multiMap.put("전송여부","0");
            }
        }else{
            multiMap.put("전송여부","0");
        }

        return multiMap;
    }


}
