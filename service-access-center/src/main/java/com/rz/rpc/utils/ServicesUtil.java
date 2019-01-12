package com.rz.rpc.utils;

import com.rz.rpc.domain.ServiceInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by as on 2018/1/17.
 * 保存服务的类
 */
public class ServicesUtil {

    private static ServicesUtil servicesUtil = null;
    private Map<String, ServiceInfo> services = null;

    private ServicesUtil() {
        services = new ConcurrentHashMap<String, ServiceInfo>();
    }

    public static ServicesUtil getServiceSingle() {
        if (servicesUtil == null) {
            synchronized (ServicesUtil.class) {
                if (servicesUtil == null) {
                    servicesUtil = new ServicesUtil();
                }
            }
        }

        return servicesUtil;
    }

    public Map<String, ServiceInfo> getServices() {
        return services;
    }
}
