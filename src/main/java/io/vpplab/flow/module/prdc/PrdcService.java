package io.vpplab.flow.module.prdc;

import io.vpplab.flow.domain.prdc.PrdcDao;
import io.vpplab.flow.domain.rsr.RsrDao;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@MapperScan(basePackages = "io.vpplab.flow.domain")
public class PrdcService {

    @Autowired
    private PrdcDao prdcDao;
    @Autowired
    private RsrDao rsrDao;


    public Map<String, Object> getPrdcAnlyList(HashMap<String,Object> paramMap, HttpServletRequest request) {
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

        HashMap getPrdcAnlyInfo  =  prdcDao.getPrdcAnlyInfo(paramMap);
        List<HashMap> prdcAnlyMap  =  prdcDao.getPrdcAnlyList(paramMap);
        int prdcAnlyTotCnt  =  prdcDao.getPrdcAnlyListCnt(paramMap);
        if(prdcAnlyMap.size() > 0){
            for(int i = 0 ; i < prdcAnlyMap.size() ; i++){
                prdcAnlyMap.get(i).put("NO",prdcAnlyTotCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("집합자원",prdcAnlyMap);
        }else{
            multiMap.put("조회여부",false);
        }
        multiMap.put("집합자원운영상태",getPrdcAnlyInfo);

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(prdcAnlyTotCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",prdcAnlyTotCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }


    public Map<String, Object> getPrdcAnlyDtl(HashMap<String,Object> paramMap, HttpServletRequest request) {
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
            tab1List = prdcDao.tab1PrdcList01(paramMap);
        }else if("02".equals(paramMap.get("조회구분"))){
            tab1List = prdcDao.tab1PrdcList02(paramMap);
        }else if("03".equals(paramMap.get("조회구분"))){
            tab1List = prdcDao.tab1PrdcList03(paramMap);
        }
        List<HashMap> tab2List = prdcDao.tab2PrdcList(paramMap);
        int tab2ListCnt  =  prdcDao.tab2PrdcListCnt(paramMap);

        if(tab2List.size() > 0){
            for(int i = 0 ; i < tab2List.size() ; i++){
                tab2List.get(i).put("NO",tab2ListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
        }
        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(tab2ListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",tab2ListCnt+"");
        /*****************페이징*********************/

        HashMap rsrInfo = prdcDao.getPrdcAnlyRsrInfo(paramMap);
        HashMap clcRsrDtl = prdcDao.getPrdcAnlyDtl(paramMap);

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
    public Map<String, Object> getPrdcAnlyDtlList(HashMap<String,Object> paramMap, HttpServletRequest request) {
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
        List<HashMap> tab2List = prdcDao.tab2PrdcList(paramMap);
        int tab2ListCnt  =  prdcDao.tab2PrdcListCnt(paramMap);

        if(tab2List.size() > 0){
            for(int i = 0 ; i < tab2List.size() ; i++){
                tab2List.get(i).put("NO",tab2ListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
        }
        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(tab2ListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",tab2ListCnt+"");
        /*****************페이징*********************/

        if(tab2List.size() >  0){
            multiMap.put("조회여부",true);
            multiMap.put("소속자원관리",tab2List);
        }else{
            multiMap.put("소속자원관리",null);
            multiMap.put("조회여부",false);
        }
        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }
    public Map<String, Object> setPrdcAnly(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        paramMap.put("중개사업자ID",loginInfo.get("중개사업자ID"));
        paramMap.put("작성자",loginInfo.get("사용자식별자"));
        int cnt = prdcDao.setPrdcAnly(paramMap);
        if(cnt > 0){
            paramMap.put("집합자원ID",paramMap.get("id"));
            int menocnt = prdcDao.setPrdcAnlyMeno(paramMap);
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }
    public Map<String, Object> setPrdcAnlySave(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        paramMap.put("중개사업자ID",loginInfo.get("중개사업자ID"));
        paramMap.put("작성자",loginInfo.get("사용자식별자"));
        int cnt = prdcDao.setPrdcAnlySave(paramMap);
        if(cnt > 0){
            int menocnt = prdcDao.setPrdcAnlyMenoSave(paramMap);
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }
    public Map<String, Object> setPrdcAnlyDel(HashMap<String,Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> multiMap = new HashMap<>();

        HashMap<String,Object> loginInfo = (HashMap) session.getAttribute("사용자정보");
        paramMap.put("중개사업자ID",loginInfo.get("중개사업자ID"));
        prdcDao.setPrdcAnlyMenoDel(paramMap);
        prdcDao.setPrdcAnlyListDel(paramMap);
        int cnt =  prdcDao.setPrdcAnlyDel(paramMap);
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }

    public Map<String, Object> getPrdcAnlyPlantList(HashMap<String,Object> paramMap, HttpServletRequest request) {
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
        List<HashMap> getRsrPlantONList = prdcDao.getPrdcAnlyPlantONList(paramMap);
        List<HashMap> getRsrPlantList = prdcDao.getPrdcAnlyPlantList(paramMap);
        int getRsrPlantListCnt  =  prdcDao.getPrdcAnlyPlantListCnt(paramMap);

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



    public Map<String, Object> getPrdcAnlyPlantSchList(HashMap<String,Object> paramMap, HttpServletRequest request) {
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
        List<HashMap> getRsrPlantList = prdcDao.getPrdcAnlyPlantList(paramMap);
        int getRsrPlantListCnt  =  prdcDao.getPrdcAnlyPlantListCnt(paramMap);

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



    public Map<String, Object> setPrdcAnlyPlant(List<HashMap<String,Object>> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        int cnt = 1;
        for(int i = 0 ; i < paramMap.size(); i++){
            HashMap<String,Object> param = new HashMap<>();
            param.put("집합자원ID",paramMap.get(i).get("집합자원ID"));
            param.put("자원ID",paramMap.get(i).get("자원ID"));
            int arrCnt = prdcDao.setPrdcAnlyPlant(param);
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

    public Map<String, Object> setPrdcAnlyPlantDel(List<HashMap<String,Object>> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();
        int cnt = 1;
        for(int i = 0 ; i < paramMap.size(); i++){
            HashMap<String,Object> param = new HashMap<>();
            param.put("집합자원ID",paramMap.get(i).get("집합자원ID"));
            param.put("자원ID",paramMap.get(i).get("자원ID"));
            int arrCnt = prdcDao.setPrdcAnlyPlantDel(param);
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
}

