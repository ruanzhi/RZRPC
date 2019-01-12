package com.rz.rpc;

import com.rz.rpc.consumer.service.RZRPCConsumer;
import com.rz.rpc.domain.Param;
import com.rz.rpc.domain.Result;
import com.rz.rpc.facade.CaculatorService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        rzrpc2Test();
    }

    public static void rzrpc1Test() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        RZRPCConsumer rzrpcConsumer = (RZRPCConsumer) context.getBean("rzrpcConsumer");
        List<Object> params = new ArrayList<Object>();
        params.add(1.0);
        params.add(2.0);
        Double result = (Double) rzrpcConsumer.serviceConsumer("add", params);
        System.out.println("add:" + result);
        params = new ArrayList<Object>();
        Param param = new Param();
        param.setN1(1.0);
        param.setN2(2.0);
        params.add(param);
        Result res = (Result) rzrpcConsumer.serviceConsumer("multiply", params);
        System.out.println("multiply:" + res.getResult());
    }

    public static void rzrpc2Test() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        CaculatorService caculatorService = (CaculatorService) context.getBean("caculatorService");
        List<Object> params = new ArrayList<Object>();
        params.add(1.0);
        params.add(2.0);
        Double result = caculatorService.add(1.0, 3.0);
        System.out.println("add:" + result);
        params = new ArrayList<Object>();
        Param param = new Param();
        param.setN1(1.0);
        param.setN2(2.0);
        params.add(param);
        Result res = caculatorService.multiply(param);
        System.out.println("multiply:" + res.getResult());
    }
}
