package com.admin.data;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCro {

    @RequestMapping("/data")
    public void test(){
        System.out.println("success---------------------------------》");
    }
}
