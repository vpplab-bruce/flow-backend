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
        List<HashMap> tab1List = rsrDao.tab1List(paramMap);
        List<HashMap> tab2List = rsrDao.tab2List(paramMap);
        int tab2ListCnt  =  rsrDao.tab2ListCnt(paramMap);

        if(tab2List.size() > 0){
            for(int i = 0 ; i < tab2List.size() ; i++){
                tab2List.get(i).put("NO",tab2ListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
        }
        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(tab2ListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",tab2ListCnt+"");
        /*****************페이징*********************/

        HashMap rsrInfo = rsrDao.getRsrInfo(paramMap);
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
        multiMap.put("페이지정보",pageInfo);
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
    public Map<String, Object> setRsrPlantDel(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        int cnt = rsrDao.setRsrPlantDel(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }

        return multiMap;
    }

    public Map<String, Object> getRsrPlantList(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        paramMap.put("facility_type",paramMap.get("설비구분"));
        paramMap.put("status",paramMap.get("발전상태"));
        paramMap.put("name",paramMap.get("발전소명"));
        paramMap.put("stat_capacity",paramMap.get("시작설비용량"));
        paramMap.put("end_capacity",paramMap.get("종료설비용량"));
        paramMap.put("stat_avg_error_rate",paramMap.get("시작평균오차율"));
        paramMap.put("end_avg_error_rate",paramMap.get("종료평균오차율"));

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
        List<HashMap> getRsrPlantONList = rsrDao.getRsrPlantONList(paramMap);
        List<HashMap> getRsrPlantList = rsrDao.getRsrPlantList(paramMap);
        int getRsrPlantListCnt  =  rsrDao.getRsrPlantListCnt(paramMap);

        if(getRsrPlantList.size() > 0){
            for(int i = 0 ; i < getRsrPlantList.size() ; i++){
                getRsrPlantList.get(i).put("NO",getRsrPlantListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
        }
        String capacity = "0";
        if(getRsrPlantONList.size() > 0){
            for(int i = 0 ; i < getRsrPlantONList.size() ; i++){
                capacity = Float.parseFloat(capacity)+(Float.parseFloat(getRsrPlantONList.get(i).get("설비용량").toString()) +  Float.parseFloat(getRsrPlantONList.get(i).get("ESS용량").toString()))+"";
            }
        }
        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(getRsrPlantListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",getRsrPlantListCnt+"");
        /*****************페이징*********************/

        if(getRsrPlantList.size() > 0){
            multiMap.put("조회여부",true);
            multiMap.put("집합자원등록발전소",getRsrPlantList);
            multiMap.put("집합자원발전소",getRsrPlantONList);
        }else{
            multiMap.put("조회여부",false);
            multiMap.put("집합자원등록발전소",null);
            multiMap.put("집합자원발전소",null);
        }
        if(getRsrPlantONList.size() > 0){
            multiMap.put("집합자원발전소",getRsrPlantONList);
            multiMap.put("참여자원용량",capacity);
        }else{
            multiMap.put("집합자원발전소",null);
        }
        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }
    public Map<String, Object> setRsrPlant(List<HashMap<String,Object>> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        int cnt = 1;
        for(int i = 0 ; i < paramMap.size(); i++){
            HashMap<String,Object> param = new HashMap<>();
            param.put("집합자원ID",paramMap.get(i).get("집합자원ID"));
            param.put("자원ID",paramMap.get(i).get("자원ID"));
            int arrCnt = rsrDao.setRsrPlant(param);
            if(arrCnt == 0){
                cnt = 0;
            }
        }
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }

        return multiMap;
    }

    public Map<String, Object> getPlantBusiList(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

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

        paramMap.put("name",paramMap.get("발전사업자명"));
        paramMap.put("business_registration_no",paramMap.get("사업자등록번호"));
        paramMap.put("ceo",paramMap.get("대표자명"));
        paramMap.put("manager",paramMap.get("담당자명"));
        paramMap.put("manager_contract",paramMap.get("담당자전화번호"));
        paramMap.put("address",paramMap.get("주소"));


        List<HashMap> getPlantBusiList  =  rsrDao.getPlantBusiList(paramMap);
        int getPlantBusiListCnt  =  rsrDao.getPlantBusiListCnt(paramMap);


        ;
        if(getPlantBusiList.size() > 0){
            for(int i = 0 ; i < getPlantBusiList.size() ; i++){
                getPlantBusiList.get(i).put("NO",getPlantBusiListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("발전자원",getPlantBusiList);
        }else{
            multiMap.put("조회여부",false);
        }

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(getPlantBusiListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",getPlantBusiListCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }
}
