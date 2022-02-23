package io.vpplab.flow.module.cmn;

import io.vpplab.flow.domain.cmn.CmnDao;
import io.vpplab.flow.domain.utils.MailUtil;
import io.vpplab.flow.domain.utils.PagingUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailAddr;

    public Map<String, Object> getLogin(HashMap<String,Object> paramMap,HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();
        List<Map<String, Object>> menuTree = new ArrayList<Map<String, Object>>();
        HashMap<String,Object> loginMap  =  cmnDao.getLogin(paramMap);
        List<HashMap> menuList  =  cmnDao.getMenuMng(paramMap);
        List<HashMap> codeList = cmnDao.getAdminCode();

        int cnt =0;
        if(loginMap != null){

            //비밀번호 비교 비밀번호실패시 비밀번호 오류건수 업데이트
            //비밀번호 비교 암호화 확인 필요


            // 비번이 맞을경우
            session.setAttribute("사용자정보", loginMap);
            if(menuList.size() > 0){
                for(int i = 0; i < menuList.size(); i++){
                    if("1".equals(menuList.get(i).get("기본메뉴여부").toString())){
                        Map<String, Object> dep1Map = new HashMap<>();
                        dep1Map.put( "메뉴ID",menuList.get(i).get("메뉴ID"));
                        dep1Map.put( "URL주소",menuList.get(i).get("URL주소"));
                        dep1Map.put( "화면이름",menuList.get(i).get("화면이름"));
                        dep1Map.put( "화면여부",menuList.get(i).get("화면여부"));
                        dep1Map.put( "수정권한여부",menuList.get(i).get("수정권한여부"));
                        menuTree.add(cnt,dep1Map);
                        List<HashMap> sub  =  new ArrayList<>();
                        for(int j = 0; j < menuList.size(); j++){
                            if(!"1".equals(menuList.get(j).get("기본메뉴여부").toString()) && ((menuList.get(i).get("1차메뉴")).equals(menuList.get(j).get("2차메뉴")))){
                                HashMap dep2Map = new HashMap<String,Object>();
                                dep2Map.put( "메뉴ID",menuList.get(j).get("메뉴ID"));
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
            multiMap.put("조회여부",true);
            multiMap.put("공통코드",codeList);
        }else{
            multiMap.put("로그인정보",null);
            multiMap.put("1차메뉴",null);
            multiMap.put("조회여부",false);
        }

        return multiMap;
    }
    @SneakyThrows
    public Map<String, Object> setPswdInit(HashMap<String,Object> paramMap, HttpServletRequest request) {

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
            infoMap.put("로그인ID",paramMap.get("로그인ID").toString());
            infoMap.put("이메일",paramMap.get("이메일").toString());
            int cnt = cmnDao.setMyPswd(infoMap);
            if(cnt > 0){
                multiMap.put("전송여부",true);
                HashMap<String, Object> msgMap = new HashMap<>();
                msgMap.put("코드","M_01");
                String msg =  cmnDao.getMsg(msgMap);
                msg =  msg.replace("{{pswdInit}}",pswdInit);
                msg =  msg.replace("\n","<br/>");
                MailUtil mailHandler = new MailUtil(javaMailSender);
                mailHandler.setTo(paramMap.get("이메일").toString());
                mailHandler.setFrom(mailAddr);
                mailHandler.setSubject("[VPPLAB 임시비밀번호 안내]");
                String htmlContent = "<p>"+msg+"<p>";
                mailHandler.setText(htmlContent, true);
                mailHandler.send();
            }else{
                multiMap.put("전송여부",false);
            }
        }else{
            multiMap.put("전송여부",false);
        }

        return multiMap;
    }

    public Map<String, Object> getPltfMng(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        HttpSession session = request.getSession();
        /******************페이징*********************/
        String pageNo = "1";
        String rowCnt = "10";
        if(paramMap.get("페이지번호") != null){
            pageNo = paramMap.get("페이지번호").toString();
        }
        if(paramMap.get("행갯수") != null){
            rowCnt = paramMap.get("행갯수").toString();
        }
        paramMap.put("페이지번호", PagingUtil.schPageNo(Integer.parseInt(pageNo),Integer.parseInt(rowCnt)));
        paramMap.put("행갯수",Integer.parseInt(rowCnt));

        HashMap<String,String> pageInfo = new HashMap<>();
        pageInfo.put("페이지번호",pageNo);
        pageInfo.put("행갯수",rowCnt);
        /*****************페이징**********************/

        List<HashMap> getPltfMng  =  cmnDao.getPltfMng(paramMap);
        int getPltfMngCnt  =  cmnDao.getPltfMngCnt(paramMap);
        ;
        if(getPltfMng.size() > 0){
            for(int i = 0 ; i < getPltfMng.size() ; i++){
                getPltfMng.get(i).put("NO",getPltfMngCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("플랫폼등록관리",getPltfMng);
        }else{
            multiMap.put("조회여부",false);
        }

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(getPltfMngCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",getPltfMngCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }
    public Map<String, Object> getPltfMngDtl(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        HashMap getPltfMngDtl  =  cmnDao.getPltfMngDtl(paramMap);
        ;
        if(getPltfMngDtl != null){
            multiMap.put("조회여부",true);
            multiMap.put("플랫폼등록관리상세",getPltfMngDtl);
        }else{
            multiMap.put("조회여부",false);
        }
        return multiMap;
    }
}
