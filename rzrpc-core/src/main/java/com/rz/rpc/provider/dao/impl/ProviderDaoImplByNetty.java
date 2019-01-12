package com.rz.rpc.provider.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.rz.rpc.provider.dao.ProviderDaoByNetty;
import com.rz.rpc.provider.dao.ProviderDaoByNio;
import com.rz.rpc.thread.NIO.Server.NIOServerThread;
import com.rz.rpc.thread.Netty.NettyServer;
import com.rz.rpc.utils.Constant;
import com.rz.rpc.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by as on 2018/1/17.
 */
public class ProviderDaoImplByNetty implements ProviderDaoByNetty {

    public boolean startListenByNetty() {
        new Thread(new Runnable() {
            public void run() {
                NettyServer nettyServer = new NettyServer();
                try {
                    nettyServer.bind();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return true;
    }

    public boolean registryService(String interfaceName, String version, String implClassName, String ip) {
        //注册到服务注册查找中心的同时也要缓存到内存services
        //step1. 注册到服务中心
        String url = "http://" + Constant.SERVICEACCESSCENTER_IP + ":" + Constant.SERVICEACCESSCENTER_PORT + "/"
                + Constant.REGISTRYSERVICE;
        Map<String, String> param = new HashMap();
        param.put("interfaceName", interfaceName);
        param.put("version", version);
        param.put("implClassName", implClassName);
        param.put("ip", ip);
        String result = HttpClientUtil.doPost(url, param);
        System.out.println("注册服务返回值：" + result);
        JSONObject json = JSONObject.parseObject(result);
        if (!json.getBoolean("result")) {
            throw new RuntimeException("注册失败...");
        }
        return true;
    }
}
