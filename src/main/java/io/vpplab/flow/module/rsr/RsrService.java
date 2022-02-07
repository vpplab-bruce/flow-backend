package io.vpplab.flow.module.rsr;

import io.vpplab.flow.domain.cmn.CmnDao;
import io.vpplab.flow.domain.rsr.RsrDao;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@MapperScan(basePackages = "io.vpplab.flow.domain")
public class RsrService {

    @Autowired
    private RsrDao rsrDao;

    public List<HashMap> getClcRsrList(HashMap<String,String> paramMap) {
        List<HashMap> hashMap  =  rsrDao.getClcRsrList(paramMap);
        return hashMap;
    }

}
