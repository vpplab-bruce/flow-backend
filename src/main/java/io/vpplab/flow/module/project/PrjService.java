package io.vpplab.flow.module.project;

import io.vpplab.flow.domain.cmn.CmnDao;
import io.vpplab.flow.domain.project.PrjDao;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@MapperScan(basePackages = "io.vpplab.flow.domain")
public class PrjService {

    @Autowired
    private PrjDao prjDao;



}
