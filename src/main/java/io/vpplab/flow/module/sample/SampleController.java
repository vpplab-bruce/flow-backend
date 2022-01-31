package io.vpplab.flow.module.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @RequestMapping("/getUsers")
    public List<HashMap> test() {
        return sampleService.getUsers();
    }

    @GetMapping("/tests")
    public String tests() {
        return "TESTS ARE ON GOING!!";
    }
}
