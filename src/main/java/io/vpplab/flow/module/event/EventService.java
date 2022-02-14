package io.vpplab.flow.module.event;

import io.vpplab.flow.domain.cmn.CmnDao;
import io.vpplab.flow.domain.event.EventDao;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@MapperScan(basePackages = "io.vpplab.flow.domain")
public class EventService {

    @Autowired
    private EventDao eventDao;


    public Map<String, Object> getEventList(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        paramMap.put("name",paramMap.get("발전자원명"));
        paramMap.put("requested_name",paramMap.get("요청자명"));
        paramMap.put("requested_phone",paramMap.get("요청자전화번호"));
        paramMap.put("type",paramMap.get("요청구분"));
        paramMap.put("status",paramMap.get("처리상태"));
        paramMap.put("requested_at",paramMap.get("처리일"));
        paramMap.put("approved_at",paramMap.get("등록일"));


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

        List<HashMap> eventList  =  eventDao.getEventList(paramMap);
        int eventListCnt  =  eventDao.getEventListCnt(paramMap);
        ;
        if(eventList.size() > 0){
            for(int i = 0 ; i < eventList.size() ; i++){
                eventList.get(i).put("NO",eventListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("이벤트정보",eventList);
        }else{
            multiMap.put("조회여부",false);
        }

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(eventListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",eventListCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }
    public Map<String, Object> getEventDtl(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        HashMap eventDtl  =  eventDao.getEventDtl(paramMap);
        ;
        if(eventDtl != null){
            multiMap.put("조회여부",true);
            multiMap.put("이벤트정보상세",eventDtl);
        }else{
            multiMap.put("조회여부",false);
        }
        return multiMap;
    }
    public Map<String, Object> setEventSave(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

         int cnt =  eventDao.setEventSave(paramMap);
        ;
        if(cnt > 0){
            multiMap.put("성공여부",true);
        }else{
            multiMap.put("성공여부",false);
        }
        return multiMap;
    }


    public Map<String, Object> getEventRsrList(HashMap<String,Object> paramMap, HttpServletRequest request) {
        Map<String, Object> multiMap = new HashMap<>();

        paramMap.put("name",paramMap.get("발전자원명"));
        paramMap.put("kpx_resource_id",paramMap.get("분산자원코드"));
        paramMap.put("manager",paramMap.get("담당자명"));
        paramMap.put("manager_contract",paramMap.get("담당자전화번호"));


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

        List<HashMap> getEventRsrList  =  eventDao.getEventRsrList(paramMap);
        int getEventRsrListCnt  =  eventDao.getEventRsrListCnt(paramMap);
        ;
        if(getEventRsrList.size() > 0){
            for(int i = 0 ; i < getEventRsrList.size() ; i++){
                getEventRsrList.get(i).put("NO",getEventRsrListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("이벤트발전자원정보",getEventRsrList);
        }else{
            multiMap.put("조회여부",false);
        }

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(getEventRsrListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",getEventRsrListCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }

}

