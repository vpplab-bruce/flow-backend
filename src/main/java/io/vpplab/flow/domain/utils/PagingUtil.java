package io.vpplab.flow.domain.utils;

import lombok.experimental.UtilityClass;

/**
 * @author archmagece
 * @since 2017-08-30
 */
@UtilityClass

public class PagingUtil {


    /**
     * 페이징설정
     */
    public static int pageCnt(int totCnt , int rowCnt) {
        int totPageCnt = 1;
        totPageCnt = totCnt / rowCnt;
        if((totCnt % rowCnt) != 0 ){
            totPageCnt =  totPageCnt + 1;
        }

        return totPageCnt;
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws  Exception {


    }



}
