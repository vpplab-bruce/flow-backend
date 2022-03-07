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

        HashMap getClcRsrInfo  =  rsrDao.getClcRsrInfo(paramMap);
        List<HashMap> clcRsrMap  =  rsrDao.getClcRsrList(paramMap);
        int clcRsrTotCnt  =  rsrDao.getClcRsrListCnt(paramMap);
        if(clcRsrMap.size() > 0){
            for(int i = 0 ; i < clcRsrMap.size() ; i++){
                clcRsrMap.get(i).put("NO",clcRsrTotCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("집합자원",clcRsrMap);
        }else{
            multiMap.put("조회여부",false);
        }
        multiMap.put("집합자원운영상태",getClcRsrInfo);

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

    public Map<String, Object> setClcRsrSave(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        paramMap.put("중개사업자ID",loginInfo.get("중개사업자ID"));
        paramMap.put("작성자",loginInfo.get("사용자식별자"));

        int cnt = rsrDao.setClcRsrSave(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }

        return multiMap;
    }
    public Map<String, Object> setClcRsrDel(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        paramMap.put("중개사업자ID",loginInfo.get("중개사업자ID"));
        paramMap.put("작성자",loginInfo.get("사용자식별자"));

        int cnt = rsrDao.setClcRsrDel(paramMap);
        if(cnt > 0){
            rsrDao.setClcRsrListDel(paramMap);
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
        List<HashMap> tab1List = null;
        if(paramMap.get("조회구분") == null || "".equals(paramMap.get("조회구분")) || "01".equals(paramMap.get("조회구분"))){
             tab1List = rsrDao.tab1List01(paramMap);
        }else if("02".equals(paramMap.get("조회구분"))){
            tab1List = rsrDao.tab1List02(paramMap);
        }else if("03".equals(paramMap.get("조회구분"))){
             tab1List = rsrDao.tab1List03(paramMap);
        }
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

        if("".equals(paramMap.get("메모행갯수")) || null == paramMap.get("메모행갯수")){
            pageInfo.put("메모행갯수",10+"");
            paramMap.put("메모행갯수",10);
        }else{
            int getClcRsrMemoListCnt = rsrDao.getClcRsrMemoListCnt(paramMap);
            if(getClcRsrMemoListCnt > Integer.parseInt(paramMap.get("메모행갯수").toString()) ){
                pageInfo.put("메모행갯수",(Integer.parseInt(paramMap.get("메모행갯수").toString())+10)+"");
                paramMap.put("메모행갯수",(Integer.parseInt(paramMap.get("메모행갯수").toString())+10));
            }

        }

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
    public Map<String, Object> getClcRsrMemoList(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        HashMap<String,String> pageInfo = new HashMap<>();

        if("".equals(paramMap.get("메모행갯수")) || null == paramMap.get("메모행갯수")){
            pageInfo.put("메모행갯수",10+"");
            paramMap.put("메모행갯수",10);
        }else{
            int getClcRsrMemoListCnt = rsrDao.getClcRsrMemoListCnt(paramMap);
            if(getClcRsrMemoListCnt > Integer.parseInt(paramMap.get("메모행갯수").toString()) ){
                pageInfo.put("메모행갯수",(Integer.parseInt(paramMap.get("메모행갯수").toString())+10) +"");
                paramMap.put("메모행갯수",(Integer.parseInt(paramMap.get("메모행갯수").toString())+10));
            }

        }

        List<HashMap> clcRsrMemoList = rsrDao.getClcRsrMemoList(paramMap);
        multiMap.put("메모",clcRsrMemoList);
        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }

    public Map<String, Object> setClcRsrMemo(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        int cnt = rsrDao.setClcRsrMemo(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }

        return multiMap;
    }
    public Map<String, Object> setClcRsrMemoDel(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        int cnt = rsrDao.setClcRsrMemoDel(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
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



    public Map<String, Object> getRsrPlantSchList(HashMap<String,Object> paramMap, HttpServletRequest request) {
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
        List<HashMap> getRsrPlantList = rsrDao.getRsrPlantList(paramMap);
        int getRsrPlantListCnt  =  rsrDao.getRsrPlantListCnt(paramMap);

        if(getRsrPlantList.size() > 0){
            for(int i = 0 ; i < getRsrPlantList.size() ; i++){
                getRsrPlantList.get(i).put("NO",getRsrPlantListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
        }
        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(getRsrPlantListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",getRsrPlantListCnt+"");
        /*****************페이징*********************/

        if(getRsrPlantList.size() > 0){
            multiMap.put("조회여부",true);
            multiMap.put("집합자원등록발전소",getRsrPlantList);
        }else{
            multiMap.put("조회여부",false);
            multiMap.put("집합자원등록발전소",null);
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
    public Map<String, Object> getBusiChk(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        int cnt = rsrDao.getBusiChk(paramMap);
        if(cnt > 0){
            multiMap.put("중복여부",true);
        }else{
            multiMap.put("중복여부",false);
        }

        return multiMap;
    }
    public Map<String, Object> getPlantBusiDtl(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        HashMap getPlantBusiDtl = rsrDao.getPlantBusiDtl(paramMap);

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

        paramMap.put("facility_type",paramMap.get("설비구분"));
        paramMap.put("status",paramMap.get("발전상태"));
        paramMap.put("ess_status",paramMap.get("ESS운영상태"));
        paramMap.put("has_brokerage_contract",paramMap.get("중개시장참여"));
        paramMap.put("previous_dealing_type",paramMap.get("기존거래방식"));
        paramMap.put("name",paramMap.get("발전자원명"));


        List<HashMap> getPlantBusiRsrList  =  rsrDao.getPlantBusiRsrList(paramMap);
        int getPlantBusiRsrListCnt  =  rsrDao.getPlantBusiRsrListCnt(paramMap);

        if(getPlantBusiRsrList.size() > 0){
            for(int i = 0 ; i < getPlantBusiRsrList.size() ; i++){
                getPlantBusiRsrList.get(i).put("NO",getPlantBusiRsrListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("발전사업자자원",getPlantBusiRsrList);
        }else{
        }

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(getPlantBusiRsrListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",getPlantBusiRsrListCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);


        if(getPlantBusiDtl != null){
            multiMap.put("조회여부",true);
            multiMap.put("발전자원상세",getPlantBusiDtl);
        }else{
            multiMap.put("조회여부",false);
        }

        return multiMap;
    }
    public Map<String, Object> setPlantBusiDel(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        int cnt = rsrDao.setPlantBusiDel(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }

        return multiMap;
    }
    public Map<String, Object> setPlantBusiAdd(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        paramMap.put("작성자",loginInfo.get("사용자식별자"));
        int cnt = rsrDao.setPlantBusiAdd(paramMap);
        if(cnt > 0){
            multiMap.put("발전사업자ID",paramMap.get("id"));
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }

        return multiMap;
    }
    public Map<String, Object> setPlantBusiSave(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        paramMap.put("작성자",loginInfo.get("사용자식별자"));
        int cnt = rsrDao.setPlantBusiSave(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }

        return multiMap;
    }

    public Map<String, Object> getPlantBusiRsrList(HashMap<String,Object> paramMap, HttpServletRequest request) {
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

        paramMap.put("facility_type",paramMap.get("설비구분"));
        paramMap.put("status",paramMap.get("발전상태"));
        paramMap.put("ess_status",paramMap.get("ESS운영상태"));
        paramMap.put("has_brokerage_contract",paramMap.get("중개시장참여"));
        paramMap.put("previous_dealing_type",paramMap.get("기존거래방식"));
        paramMap.put("name",paramMap.get("발전자원명"));


        List<HashMap> getPlantBusiRsrList  =  rsrDao.getPlantBusiRsrList(paramMap);
        int getPlantBusiRsrListCnt  =  rsrDao.getPlantBusiRsrListCnt(paramMap);

        if(getPlantBusiRsrList.size() > 0){
            for(int i = 0 ; i < getPlantBusiRsrList.size() ; i++){
                getPlantBusiRsrList.get(i).put("NO",getPlantBusiRsrListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("발전사업자자원",getPlantBusiRsrList);
        }else{
            multiMap.put("조회여부",false);
        }

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(getPlantBusiRsrListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",getPlantBusiRsrListCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }
    public Map<String, Object> getPlantBusiRsrAdd(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        paramMap.put("작성자",loginInfo.get("사용자식별자"));
        int cnt = rsrDao.getPlantBusiRsrAdd(paramMap);
        if(cnt > 0){
            paramMap.put("발전자원ID",paramMap.get("id"));
            int metaCnt = rsrDao.getPlantBusiRsrMetaAdd(paramMap);
            if(metaCnt > 0){
                multiMap.put("성공여부",true);
            }else{
                multiMap.put("성공여부",false);
            }
        }else{
            multiMap.put("성공여부",false);
        }

        return multiMap;
    }
    public Map<String, Object> getPlantBusiRsrDtl(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        HashMap getPlantBusiRsrDtl = rsrDao.getPlantBusiRsrDtl(paramMap);
        if(getPlantBusiRsrDtl != null){
            multiMap.put("조회여부",true);
            multiMap.put("발전사업자자원상세",getPlantBusiRsrDtl);
        }else{
            multiMap.put("조회여부",false);
        }
        return multiMap;
    }
    public Map<String, Object> setPlantBusiRsrSave(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        int cnt = rsrDao.setPlantBusiRsrSave(paramMap);
        if(cnt > 0){
            int metaCnt = rsrDao.setPlantBusiRsrMetaSave(paramMap);
            if(metaCnt > 0){
                multiMap.put("성공여부",true);
            }else{
                multiMap.put("성공여부",false);
            }
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }

    public Map<String, Object> setPlantBusiRsrDel(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        int cnt = rsrDao.setPlantBusiRsrDel(paramMap);
        if(cnt > 0){
            int metaCnt = rsrDao.setPlantBusiRsrMetaDel(paramMap);
            if(metaCnt > 0){
                multiMap.put("성공여부",true);
            }else{
                multiMap.put("성공여부",false);
            }
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }


    public Map<String, Object> getPtrRsrList(HashMap<String,Object> paramMap, HttpServletRequest request) {
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

        paramMap.put("facility_type",paramMap.get("설비구분"));
        paramMap.put("status",paramMap.get("발전상태"));
        paramMap.put("ess_status",paramMap.get("ESS운영상태"));
        paramMap.put("name",paramMap.get("발전자원명"));
        paramMap.put("kpx_resource_id",paramMap.get("분산자원코드"));
        paramMap.put("start_error_rate",paramMap.get("시작평균오차율"));
        paramMap.put("end_error_rate",paramMap.get("종료평균오차율"));
        paramMap.put("rsr_name",paramMap.get("집합자원그룹명"));

        List<HashMap> getPtrRsrList  =  rsrDao.getPtrRsrList(paramMap);
        int getPtrRsrListCnt  =  rsrDao.getPtrRsrListCnt(paramMap);

        if(getPtrRsrList.size() > 0){
            for(int i = 0 ; i < getPtrRsrList.size() ; i++){
                getPtrRsrList.get(i).put("NO",getPtrRsrListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("참여자원",getPtrRsrList);
        }else{
            multiMap.put("조회여부",false);
        }

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(getPtrRsrListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",getPtrRsrListCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }

    public Map<String, Object> getPtrRsrListPop(HashMap<String,Object> paramMap, HttpServletRequest request) {
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

        paramMap.put("facility_type",paramMap.get("설비구분"));
        paramMap.put("status",paramMap.get("발전상태"));
        paramMap.put("ess_status",paramMap.get("ESS운영상태"));
        paramMap.put("name",paramMap.get("발전자원명"));
        paramMap.put("has_brokerage_contract",paramMap.get("중개시장참여"));
        paramMap.put("previous_dealing_type",paramMap.get("기존거래방식"));


        List<HashMap> getPtrRsrListPop  =  rsrDao.getPtrRsrListPop(paramMap);
        int getPtrRsrListPopCnt  =  rsrDao.getPtrRsrListPopCnt(paramMap);

        if(getPtrRsrListPop.size() > 0){
            for(int i = 0 ; i < getPtrRsrListPop.size() ; i++){
                getPtrRsrListPop.get(i).put("NO",getPtrRsrListPopCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("참여자원발전소목록",getPtrRsrListPop);
        }else{
            multiMap.put("조회여부",false);
        }

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(getPtrRsrListPopCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",getPtrRsrListPopCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }
    public Map<String, Object> getPtrRsrAddDtl(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        HashMap getPtrRsrInfo  =  rsrDao.getPtrRsrAddDtl(paramMap);

        if(getPtrRsrInfo != null){
            multiMap.put("조회여부",true);
            multiMap.put("참여자원발전소정보",getPtrRsrInfo);
        }else{
            multiMap.put("조회여부",false);
        }
        return multiMap;
    }
    public Map<String, Object> setPtrRsrAddSave(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        int cnt  =  rsrDao.setPtrRsrAddSave(paramMap);

        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }
    public Map<String, Object> setPtrRsrAddDel(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        int cnt  =  rsrDao.setPtrRsrAddDel(paramMap);

        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }
    public Map<String, Object> getPtrRsrDtl(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        HashMap getPtrRsrDtl  =  rsrDao.getPtrRsrDtl(paramMap);
        List<HashMap> getPtrRsrDtlList  =  rsrDao.getPtrRsrDtlList(paramMap);
        if(getPtrRsrDtl != null){
            multiMap.put("조회여부",true);
            multiMap.put("참여자원상세정보",getPtrRsrDtl);
            multiMap.put("참여자원상세목록",getPtrRsrDtlList);
        }else{
            multiMap.put("조회여부",false);
        }
        return multiMap;
    }
    public Map<String, Object> getPtrRsrDtlList(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        List<HashMap> getPtrRsrDtlList  =  rsrDao.getPtrRsrDtlList(paramMap);

        if(getPtrRsrDtlList.size() > 0){
            multiMap.put("조회여부",true);
            multiMap.put("참여자원상세목록",getPtrRsrDtlList);
        }else{
            multiMap.put("조회여부",false);
        }
        return multiMap;
    }

}
