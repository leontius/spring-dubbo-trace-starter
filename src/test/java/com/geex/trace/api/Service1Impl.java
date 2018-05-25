package com.geex.trace.api;

import org.springframework.beans.factory.annotation.Autowired;


public class Service1Impl implements Service1 {
    @Autowired
    Service2 service2;

    @Override
    public void hi(String msg) {
        System.out.println("Service1.hi");
        System.out.println(msg);
        service2.hi(msg);
    }
}
