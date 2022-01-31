package io.vpplab.flow.module.sample;

import io.vpplab.flow.domain.sample.SampleDao;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@MapperScan(basePackages = "io.vpplab.flow.domain")
public class SampleService {

    @Autowired
    private SampleDao sampleDao;

    public List<HashMap> getUsers() {
        return sampleDao.getUsers();
    }

    public String test() {
        return "HELLO!! THIS IS TEST STRING!!!";
    }
}
