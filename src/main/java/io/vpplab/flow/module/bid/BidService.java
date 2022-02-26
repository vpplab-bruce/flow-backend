package io.vpplab.flow.module.bid;

import io.vpplab.flow.domain.bid.BidDao;
import io.vpplab.flow.domain.cmn.CmnDao;
import io.vpplab.flow.domain.utils.MailUtil;
import io.vpplab.flow.domain.utils.PagingUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    public Map<String, Object> getSettlementList(HashMap<String,Object> paramMap, HttpServletRequest request) {
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
        paramMap.put("id",paramMap.get("발전자원ID"));
        paramMap.put("name",paramMap.get("발전자원명"));
        paramMap.put("grp_name",paramMap.get("집합자원그룹명"));
        paramMap.put("type",paramMap.get("정산구분"));
        paramMap.put("kpx_settlement_date",paramMap.get("KPX정산일"));
        paramMap.put("commission_rate",paramMap.get("계약타입"));
        List<HashMap> getSettlementList  =  bidDao.getSettlementList(paramMap);
        int getSettlementListCnt  =  bidDao.getSettlementListCnt(paramMap);
        if(getSettlementList.size() > 0){
            for(int i = 0 ; i < getSettlementList.size() ; i++){
                getSettlementList.get(i).put("NO",getSettlementListCnt - (((Integer.parseInt(pageNo)-1)*Integer.parseInt(rowCnt))+i));
            }
            multiMap.put("조회여부",true);
            multiMap.put("정산관리",getSettlementList);
        }else{
            multiMap.put("조회여부",false);
        }

        /******************페이징*********************/
        pageInfo.put("전체페이지갯수", PagingUtil.pageCnt(getSettlementListCnt,Integer.parseInt(rowCnt))+"");
        pageInfo.put("전체갯수",getSettlementListCnt+"");
        /*****************페이징*********************/

        multiMap.put("페이지정보",pageInfo);
        return multiMap;
    }
    public void getSettlementExcelList( HttpServletRequest request, HttpServletResponse response) {
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",request.getParameter("발전자원ID"));
        paramMap.put("name",request.getParameter("발전자원명"));
        paramMap.put("grp_name",request.getParameter("집합자원그룹명"));
        paramMap.put("type",request.getParameter("정산구분"));
        paramMap.put("kpx_settlement_date",request.getParameter("KPX정산일"));
        paramMap.put("commission_rate",request.getParameter("계약타입"));
        List<HashMap> getSettlementList  =  bidDao.getSettlementExcelList(paramMap);

        if(getSettlementList.size() > 0){
            for(int i = 0 ; i < getSettlementList.size() ; i++){
                getSettlementList.get(i).put("NO",getSettlementList.size() - i);
            }
            try {
                Workbook wb = new XSSFWorkbook();
                CellStyle TestStyle = wb.createCellStyle();
                TestStyle.setAlignment(HorizontalAlignment.CENTER);
                TestStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                TestStyle.setWrapText(true);
                CellStyle TestStyle2 = wb.createCellStyle();
                TestStyle2.setAlignment(HorizontalAlignment.LEFT);
                TestStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
                TestStyle2.setWrapText(true);
                Sheet sheet = wb.createSheet("정산관리");
                sheet.setColumnWidth(0,3000);
                sheet.setColumnWidth(1,3500);
                sheet.setColumnWidth(2,10000);
                sheet.setColumnWidth(3,10000);
                sheet.setColumnWidth(4,3000);
                sheet.setColumnWidth(5,4000);
                sheet.setColumnWidth(6,3000);
                sheet.setColumnWidth(7,4000);
                sheet.setColumnWidth(8,3000);
                sheet.setColumnWidth(9,4000);
                sheet.setColumnWidth(10,3000);
                sheet.setColumnWidth(11,4000);
                sheet.setColumnWidth(12,3000);
                sheet.setColumnWidth(13,4000);
                sheet.setColumnWidth(14,3000);
                Row row = null;
                Cell cell = null;
                int rowNum = 0;

                // Header
                row = sheet.createRow(rowNum++);
                cell = row.createCell(0);
                cell.setCellValue("No");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(1);
                cell.setCellValue("발전자원ID");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(2);
                cell.setCellValue("발전자원명");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(3);
                cell.setCellValue("집합자원그룹명");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(4);
                cell.setCellValue("정산구분");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(5);
                cell.setCellValue("KPX정산일");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(6);
                cell.setCellValue("전력시장발전기명");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(7);
                cell.setCellValue("전력시장ID");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(8);
                cell.setCellValue("계량전력량(kWh)");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(9);
                cell.setCellValue("전력정산금(원)");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(10);
                cell.setCellValue("계약타입");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(11);
                cell.setCellValue("수수료");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(12);
                cell.setCellValue("세금");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(13);
                cell.setCellValue("정산금(원)");
                cell.setCellStyle(TestStyle);
                cell = row.createCell(14);
                cell.setCellValue("입금일");
                cell.setCellStyle(TestStyle);

                // Body
                for (int i=0; i<getSettlementList.size(); i++) {
                    row = sheet.createRow(rowNum++);
                    cell = row.createCell(0);
                    cell.setCellValue(""+getSettlementList.get(i).get("NO"));
                    cell = row.createCell(1);
                    cell.setCellValue(""+getSettlementList.get(i).get("발전자원ID"));
                    cell = row.createCell(2);
                    cell.setCellValue(""+getSettlementList.get(i).get("발전자원명"));
                    cell.setCellStyle(TestStyle2);
                    cell = row.createCell(3);
                        cell.setCellValue(""+getSettlementList.get(i).get("집합자원그룹명"));
                    cell.setCellStyle(TestStyle);
                    cell = row.createCell(4);
                    cell.setCellValue(""+getSettlementList.get(i).get("정산구분"));
                    cell.setCellStyle(TestStyle);
                    cell = row.createCell(5);
                    cell.setCellValue(""+getSettlementList.get(i).get("KPX정산일"));
                    cell.setCellStyle(TestStyle);

                    cell = row.createCell(6);
                    cell.setCellValue(""+getSettlementList.get(i).get("전력시장발전기명"));
                    cell.setCellStyle(TestStyle);
                    cell = row.createCell(7);
                    cell.setCellValue(""+getSettlementList.get(i).get("전력시장ID"));
                    cell.setCellStyle(TestStyle);
                    cell = row.createCell(8);
                    cell.setCellValue(""+getSettlementList.get(i).get("계량전력량"));
                    cell.setCellStyle(TestStyle);
                    cell = row.createCell(9);
                    cell.setCellValue(""+getSettlementList.get(i).get("전력정산금"));
                    cell.setCellStyle(TestStyle);
                    cell = row.createCell(10);
                    cell.setCellValue(""+getSettlementList.get(i).get("계약타입"));
                    cell.setCellStyle(TestStyle);
                    cell = row.createCell(11);
                    cell.setCellValue(""+getSettlementList.get(i).get("수수료"));
                    cell.setCellStyle(TestStyle);
                    cell = row.createCell(12);
                    cell.setCellValue(""+getSettlementList.get(i).get("세금"));
                    cell.setCellStyle(TestStyle);
                    cell = row.createCell(13);
                    cell.setCellValue(""+getSettlementList.get(i).get("정산금"));
                    cell.setCellStyle(TestStyle);
                    cell = row.createCell(14);
                    cell.setCellValue(""+getSettlementList.get(i).get("입금일"));
                    cell.setCellStyle(TestStyle);
                }

                // 컨텐츠 타입과 파일명 지정
                response.setContentType("ms-vnd/excel");
                //        response.setHeader("Content-Disposition", "attachment;filename=sendMng.xls");
                response.setHeader("Content-Disposition", "attachment;filename=settlementList.xlsx");

                // Excel File Output

                wb.write(response.getOutputStream());
                wb.close();
            } catch (IOException e) {
            }

        }

    }


}
