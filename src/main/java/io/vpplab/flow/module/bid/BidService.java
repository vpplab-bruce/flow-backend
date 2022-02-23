package io.vpplab.flow.module.bid;

import io.vpplab.flow.domain.bid.BidDao;
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
public class BidService {

    @Autowired
    private BidDao bidDao;

    public Map<String, Object> getBidList(HashMap<String,Object> paramMap, HttpServletRequest request) {
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
        paramMap.put("base_date",paramMap.get("입찰일자"));
        paramMap.put("id",paramMap.get("집합자원ID"));
        paramMap.put("status",paramMap.get("운영상태"));
        paramMap.put("name",paramMap.get("집합자원그룹명"));
        paramMap.put("type",paramMap.get("집합자원구분"));
        if("01".equals(paramMap.get("오차율평가"))){
            paramMap.put("오차율평가","좋음");
        }else if("02".equals(paramMap.get("오차율평가"))){
            paramMap.put("오차율평가","보통");
        }else if("03".equals(paramMap.get("오차율평가"))){
            paramMap.put("오차율평가","나쁨");
        }
        paramMap.put("error_rate_cd",paramMap.get("오차율평가"));
        if("01".equals(paramMap.get("입찰상태"))){
            paramMap.put("입찰상태","1차입찰");
        }else if("02".equals(paramMap.get("입찰상태"))){
            paramMap.put("입찰상태","2차입찰");
        }else if("03".equals(paramMap.get("입찰상태"))){
            paramMap.put("입찰상태","미입찰");
        }
        paramMap.put("bid_type",paramMap.get("입찰상태"));
        List<HashMap> bidMap  =  bidDao.getBidList(paramMap);
        int bidMapCnt  =  bidDao.getBidListCnt(paramMap);
        if(bidMap.size() > 0){
            for(int i = 0 ; i < bidMap.size() ; i++){
                bidMap.get(i).put("NO",bidMapCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("입찰관리",bidMap);
        }else{
            multiMap.put("조회여부",false);
        }

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(bidMapCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",bidMapCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }


}
