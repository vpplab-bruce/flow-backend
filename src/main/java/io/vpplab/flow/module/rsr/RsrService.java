package io.vpplab.flow.module.rsr;

import io.vpplab.flow.domain.cmn.CmnDao;
import io.vpplab.flow.domain.rsr.RsrDao;
import io.vpplab.flow.domain.utils.PagingUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

        HashMap<String,String> pageInfo = new HashMap<>();
        pageInfo.put("페이지번호",pageNo);
        pageInfo.put("행갯수",rowCnt);
        /*****************페이징**********************/


        String userId = loginInfo.get("로그인ID").toString();
        String agency = loginInfo.get("중개사업자키").toString();
        paramMap.put("로그인ID",userId);
        paramMap.put("중개사업자키",agency);
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

}
