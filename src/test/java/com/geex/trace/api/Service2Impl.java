package com.geex.trace.api;


public class Service2Impl implements Service2 {

    @Override
    public void hi(String msg) {
        System.out.println("Service2.hi");
        System.out.println(msg);
    }
}
