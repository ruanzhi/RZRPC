package com.rz.rpc.consumer.service;

import java.util.List;

/**
 * Created by as on 2018/1/17.
 * 客户端调用暴露的api接口
 */
public interface RZRPCConsumer {

    public Object serviceConsumer(String methodName, List<Object> params) throws Exception;
}
