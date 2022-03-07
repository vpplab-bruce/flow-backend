package io.vpplab.flow.module.prdc;

import io.vpplab.flow.domain.prdc.PrdcDao;
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


}

