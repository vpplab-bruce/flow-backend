package io.vpplab.flow.module.rsr;

import io.vpplab.flow.domain.rsr.RsrDao;
import io.vpplab.flow.domain.utils.PagingUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@MapperScan(basePackages = "io.vpplab.flow.domain")
public class RsrService {

    @Autowired
    private RsrDao rsrDao;

    public Map<String, Object> getClcRsrList(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");


        if(loginInfo == null){
            multiMap.put("조회여부",false);
            return multiMap;
        }

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

        HashMap<String,Object> pageInfo = new HashMap<>();
        pageInfo.put("페이지번호",pageNo);
        pageInfo.put("행갯수",rowCnt);
        /*****************페이징**********************/

        paramMap.put("로그인ID", loginInfo.get("로그인ID"));
        paramMap.put("중개사업자ID",loginInfo.get("중개사업자ID"));
        paramMap.put("id",paramMap.get("집합자원ID"));
        paramMap.put("type",paramMap.get("집합자원구분"));
        paramMap.put("status",paramMap.get("운영상태"));
        paramMap.put("name",paramMap.get("집합자원그룹명"));
        paramMap.put("apply_type",paramMap.get("신청유형"));
        paramMap.put("apply_status",paramMap.get("처리상태"));
        paramMap.put("error_rate_cd",paramMap.get("오차율평가"));


        List<HashMap> clcRsrMap  =  rsrDao.getClcRsrList(paramMap);
        int clcRsrTotCnt  =  rsrDao.getClcRsrListCnt(paramMap);


;
        if(clcRsrMap.size() > 0){
            for(int i = 0 ; i < clcRsrMap.size() ; i++){
                clcRsrMap.get(i).put("NO",clcRsrTotCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("집합자원",clcRsrMap);
        }else{
            multiMap.put("조회여부",false);
        }

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(clcRsrTotCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",clcRsrTotCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }
    public Map<String, Object> setClcRsr(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        paramMap.put("중개사업자ID",loginInfo.get("중개사업자ID"));
        paramMap.put("작성자",loginInfo.get("사용자식별자"));

        int cnt = rsrDao.setClcRsr(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }

        return multiMap;
    }

    public Map<String, Object> getClcRsrDtl(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        HashMap rsrInfo = new HashMap<>();
        rsrInfo.put("집합자원갯수","0");
        rsrInfo.put("집합지원총용량","0");
        rsrInfo.put("오차율평가","01");
        rsrInfo.put("가동율","0");
        rsrInfo.put("평균오차율","0");
        rsrInfo.put("최대오차율","0");
        rsrInfo.put("최소오차율","0");

        List<HashMap> tab1List = rsrDao.tab1List(paramMap);
        List<HashMap> tab2List = rsrDao.tab2List(paramMap);
        HashMap clcRsrDtl = rsrDao.getClcRsrDtl(paramMap);
        List<HashMap> clcRsrMemoList = rsrDao.getClcRsrMemoList(paramMap);
        clcRsrDtl.put("메모",clcRsrMemoList);
        if(clcRsrDtl != null){
            multiMap.put("조회여부",true);
            multiMap.put("집합자원상세",clcRsrDtl);
            multiMap.put("발전량예측분석통계",rsrInfo);
            multiMap.put("발전량예측분석",tab1List);
            multiMap.put("소속자원관리",tab2List);
        }else{
            multiMap.put("발전량예측분석",null);
            multiMap.put("소속자원관리",null);
            multiMap.put("조회여부",false);
        }

        return multiMap;
    }
    public Map<String, Object> setMemo(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        int cnt = rsrDao.setMemo(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }

        return multiMap;
    }
    public Map<String, Object> setMemoDel(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        int cnt = rsrDao.setMemoDel(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }

        return multiMap;
    }

}
