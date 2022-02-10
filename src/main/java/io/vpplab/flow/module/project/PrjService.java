package io.vpplab.flow.module.project;

import io.vpplab.flow.domain.cmn.CmnDao;
import io.vpplab.flow.domain.project.PrjDao;
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
public class PrjService {

    @Autowired
    private PrjDao prjDao;
    @Autowired
    private CmnDao cmnDao;
    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String mailAddr;

    public Map<String, Object> getMyInfo(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        if(loginInfo == null){
            multiMap.put("조회여부",false);
            return multiMap;
        }
        String userId = loginInfo.get("로그인ID").toString();
        paramMap.put("로그인ID",userId);
        HashMap<String,Object> loginMap  =  prjDao.getMyInfo(paramMap);
        if(loginMap != null){
            multiMap.put("조회여부",true);
            multiMap.put("내정보",loginMap);
        }else{
            multiMap.put("조회여부",false);
        }

        return multiMap;
    }

    public Map<String, Object> setMyInfo(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        if(loginInfo == null){
            multiMap.put("성공여부",false);
            return multiMap;
        }
        String userId = loginInfo.get("로그인ID").toString();
        paramMap.put("로그인ID",userId);
        int cnt  =  prjDao.setMyInfo(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }

    public Map<String, Object> getWithdrawal(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        if(loginInfo == null){
            multiMap.put("성공여부",false);
            return multiMap;
        }
        String userId = loginInfo.get("로그인ID").toString();
        paramMap.put("로그인ID",userId);
        int cnt  =  prjDao.getWithdrawal(paramMap);
        if(cnt > 0){
            session.removeAttribute("사용자정보");
            session.invalidate();
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }
    public Map<String, Object> getUserList(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        HttpSession session = request.getSession();
        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        paramMap.put("중개사업자키",loginInfo.get("중개사업자키"));
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

        List<HashMap> userListMap  =  prjDao.getUserList(paramMap);
        int userListCnt  =  prjDao.getUserListCnt(paramMap);
        ;
        if(userListMap.size() > 0){
            for(int i = 0 ; i < userListMap.size() ; i++){
                userListMap.get(i).put("NO",userListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("사용자정보",userListMap);
        }else{
            multiMap.put("조회여부",false);
        }

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(userListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",userListCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }

    public Map<String, Object> getUserDtl(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        HashMap userMap  =  prjDao.getUserDtl(paramMap);
        if(userMap != null){
            multiMap.put("조회여부",true);
            multiMap.put("사용자정보",userMap);
        }else{
            multiMap.put("조회여부",false);
        }
        return multiMap;
    }


    public Map<String, Object> setUserSave(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        int cnt  =  prjDao.setUserSave(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }


    @SneakyThrows
    public Map<String, Object> setUserPwInit(HashMap<String,Object> paramMap, HttpServletRequest request) {

        Map<String, Object> multiMap = new HashMap<>();
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
            infoMap.put("사용자ID",paramMap.get("사용자ID"));
            int cnt = prjDao.setUserPswd(infoMap);
            HashMap userInfoMap  =  prjDao.getUserDtl(paramMap);
            if(cnt > 0){
                HashMap<String, Object> msgMap = new HashMap<>();
                msgMap.put("코드","M_01");
                String msg =  cmnDao.getMsg(msgMap);
                msg =  msg.replace("{{pswdInit}}",pswdInit);
                msg =  msg.replace("\n","<br/>");
                MailUtil mailHandler = new MailUtil(javaMailSender);
                mailHandler.setTo(userInfoMap.get("이메일").toString());
                mailHandler.setFrom(mailAddr);
                mailHandler.setSubject("[VPPLAB 임시비밀번호 안내]");
                String htmlContent = "<p>"+msg+"<p>";
                mailHandler.setText(htmlContent, true);
                mailHandler.send();

                multiMap.put("전송여부",true);
                // SMS 전송처리
            }else{
                multiMap.put("전송여부",false);
            }

        return multiMap;
    }

    public Map<String, Object> setUserDel(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        int cnt  =  prjDao.setUserDel(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }
    public Map<String, Object> setUserAdd(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        String pswdInit = "";
        SecureRandom random = new SecureRandom ();
        String certNumCreate = random.nextInt(10)+""
                + random.nextInt(10)+""
                + random.nextInt(10)+""
                + random.nextInt(10)+""
                + random.nextInt(10)+""
                + random.nextInt(10);
        pswdInit = certNumCreate;
        paramMap.put("로그인비밀번호",pswdInit);
        int cnt  =  prjDao.setUserAdd(paramMap);
        if(cnt > 0){
            
            //문자비밀번호 전송
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }
}

