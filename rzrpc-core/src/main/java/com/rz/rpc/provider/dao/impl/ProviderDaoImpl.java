package com.rz.rpc.provider.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.rz.rpc.provider.dao.ProviderDao;
import com.rz.rpc.thread.BIO.ServerThread;
import com.rz.rpc.utils.Constant;
import com.rz.rpc.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by as on 2018/1/17.
 */
public class ProviderDaoImpl implements ProviderDao {

    public boolean startListen() {
        new Thread(new ServerThread()).start();
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
