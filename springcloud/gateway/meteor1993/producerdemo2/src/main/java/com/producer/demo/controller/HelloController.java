package com.producer.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("hello")
    public String hello(@RequestParam String name){
        return "hello,name2="+name;
    }

    @RequestMapping("foo")
    public String foo(String foo){
        return "foo2="+foo;
    }
}
